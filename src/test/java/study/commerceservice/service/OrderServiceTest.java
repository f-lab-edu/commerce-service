package study.commerceservice.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.*;
import study.commerceservice.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Autowired
    OrderRepository orderRepository;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @Transactional(readOnly = true)
    public void createPreOrder() {
        // given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine1 = OrderLine.builder()
                .price(5000)
                .quantity(3)
                .productOptionId(1L)
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .price(10000)
                .quantity(2)
                .productOptionId(2L)
                .build();

        orderLines.add(orderLine1);
        orderLines.add(orderLine2);
        em.persist(orderLine1);
        em.persist(orderLine2);

        // when
        Order preOrder = Order.createPreOrder(1L, orderLines);
        orderRepository.save(preOrder);

        // then
        System.out.println("order = " + preOrder);
        Assertions.assertThat(preOrder.getMemberId()).isEqualTo(1L);
        Assertions.assertThat(preOrder.getOrderStatus()).isEqualTo(OrderStatus.PRE);
        Assertions.assertThat(preOrder.getTotalPrice()).isEqualTo(35000);
    }

    @Test
    @Transactional(readOnly = true)
    public void createOrder() {
        // given
        List<OrderLine> orderLines = new ArrayList<>();
        OrderLine orderLine1 = OrderLine.builder()
                .price(5000)
                .quantity(3)
                .productOptionId(1L)
                .build();
        OrderLine orderLine2 = OrderLine.builder()
                .price(10000)
                .quantity(2)
                .productOptionId(2L)
                .build();

        orderLines.add(orderLine1);
        orderLines.add(orderLine2);
        em.persist(orderLine1);
        em.persist(orderLine2);

        Order preOrder = Order.createPreOrder(1L, orderLines);
        orderRepository.save(preOrder);

        Address address = Address.builder()
                .zipCode("10531")
                .address1("경기도 고양시 고양고양이")
                .address2("캣타워 111동 111호")
                .build();

        Receiver receiver = Receiver.builder()
                .name("삼순이")
                .clphNo("01033333333")
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                                        .address(address)
                                        .receiver(receiver)
                                        .message("일회용수저 꼭 넣어주세요")
                                        .build();

        PaymentLine paymentLine = new PaymentLine(PaymentType.COUPON);
        List<PaymentLine> paymentLines = List.of(paymentLine);

        // when
        Order order = Order.createOrder(preOrder, shippingInfo, paymentLines);

        // then
        Assertions.assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMP);
        Assertions.assertThat(order.getShippingInfo().getReceiver().getName()).isEqualTo(receiver.getName());
        Assertions.assertThat(order.getShippingInfo().getReceiver().getClphNo()).isEqualTo(receiver.getClphNo());
        Assertions.assertThat(order.getPaymentLines().get(0).getPaymentType()).isEqualTo(paymentLine.getPaymentType());

    }
}
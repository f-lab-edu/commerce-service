package study.commerceservice.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.*;
import study.commerceservice.dto.CheckOutDto;
import study.commerceservice.dto.PreOrderDto;
import study.commerceservice.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional(readOnly = true)
class OrderServiceTest {

    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderService orderService;

    @BeforeEach
    public void init() {
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    @Transactional
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
        Order save = orderRepository.save(preOrder);

        Optional<Order> byId = orderRepository.findById(save.getId());

        // then
        assertThat(byId.get().getMemberId()).isEqualTo(1L);
        assertThat(byId.get().getOrderStatus()).isEqualTo(OrderStatus.PRE);
        assertThat(byId.get().getTotalPrice()).isEqualTo(35000);
    }

    @Test
    @Transactional
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


        em.persist(orderLine1);
        em.persist(orderLine2);

        orderLines.add(orderLine1);
        orderLines.add(orderLine2);

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
        em.persist(shippingInfo);
        
        PaymentLine paymentLine = new PaymentLine(PaymentType.COUPON);
        em.persist(paymentLine);
        List<PaymentLine> paymentLines = List.of(paymentLine);

        
        // when
        Order order = Order.createOrder(preOrder, shippingInfo, paymentLines);

        Order findOrder = orderRepository.findById(order.getId()).get();

        System.out.println("findOrder.getOrderNumber() = " + findOrder.getOrderNumber());
        
        // then
        assertThat(findOrder.getOrderStatus()).isEqualTo(OrderStatus.COMP);
        assertThat(findOrder.getOrderNumber()).isEqualTo(order.getOrderNumber());
        assertThat(findOrder.getOrderNumber()).isNotNull();
        assertThat(findOrder.getPaymentLines().get(0).getPaymentType()).isEqualTo(PaymentType.COUPON);
        assertThat(findOrder.getShippingInfo().getReceiver().getName()).isEqualTo(receiver.getName());
        assertThat(findOrder.getShippingInfo().getReceiver().getClphNo()).isEqualTo(receiver.getClphNo());
        assertThat(findOrder.getPaymentLines().get(0).getPaymentType()).isEqualTo(paymentLine.getPaymentType());

    }

    @Test
    public void orderServiceCheckoutTest() {
        //given
        PreOrderDto preOrderDto1 = new PreOrderDto();
        preOrderDto1.setProductName("코카콜라제로리뉴얼");
        preOrderDto1.setOptionName("355ml * 24");
        preOrderDto1.setPrice(30500);
        preOrderDto1.setQuantity(5);
        preOrderDto1.setProductOptionId(15L);

        PreOrderDto preOrderDto2 = new PreOrderDto();
        preOrderDto2.setProductName("맥북프로 16인치");
        preOrderDto2.setOptionName("Ram 16GB, SSD 1TB, M1X Processor");
        preOrderDto2.setPrice(3350000);
        preOrderDto2.setQuantity(2);
        preOrderDto2.setProductOptionId(23L);

        List<PreOrderDto> preOrderDtos = new ArrayList<>();
        preOrderDtos.add(preOrderDto1);
        preOrderDtos.add(preOrderDto2);

        //when
        CheckOutDto checkout = orderService.checkout(1L, preOrderDtos);

        System.out.println("checkout = " + checkout);

    }
}
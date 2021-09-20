package study.commerceservice.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.*;
import study.commerceservice.dto.*;
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
        ProductOptionDto productOptionDto1 = ProductOptionDto.builder()
                .productName("코카콜라제로리뉴얼")
                .optionName("355ml * 24")
                .price(30500)
                .quantity(5)
                .productOptionId(15L)
                .build();

        ProductOptionDto productOptionDto2 = ProductOptionDto.builder()
                .productName("맥북프로 16인치")
                .optionName("Ram 16GB, SSD 1TB, M1X Processor")
                .price(3350000)
                .quantity(2)
                .productOptionId(23L)
                .build();

        List<ProductOptionDto> productOptionDtos = new ArrayList<>();
        productOptionDtos.add(productOptionDto1);
        productOptionDtos.add(productOptionDto2);

        //when
        CheckOutDto checkout = orderService.checkout(1L, productOptionDtos);

        // then
        assertThat(checkout.getTotalPrice()).isEqualTo(30500 * 5 + 3350000 * 2);
        assertThat(checkout.getProductOptionDtos().get(0).getPrice()).isEqualTo(productOptionDto1.getPrice());
        assertThat(checkout.getProductOptionDtos().get(0).getQuantity()).isEqualTo(productOptionDto1.getQuantity());
        assertThat(checkout.getProductOptionDtos().get(1).getPrice()).isEqualTo(productOptionDto2.getPrice());
        assertThat(checkout.getProductOptionDtos().get(1).getQuantity()).isEqualTo(productOptionDto2.getQuantity());
    }
    
    @Test
    public void orderServiceOrderTest() {
        //given
        ProductOptionDto productOptionDto1 = ProductOptionDto.builder()
                .productName("코카콜라제로리뉴얼")
                .optionName("355ml * 24")
                .price(30500)
                .quantity(5)
                .productOptionId(15L)
                .build();

        ProductOptionDto productOptionDto2 = ProductOptionDto.builder()
                .productName("맥북프로 16인치")
                .optionName("Ram 16GB, SSD 1TB, M1X Processor")
                .price(3350000)
                .quantity(2)
                .productOptionId(23L)
                .build();

        List<ProductOptionDto> productOptionDtos = new ArrayList<>();
        productOptionDtos.add(productOptionDto1);
        productOptionDtos.add(productOptionDto2);

        CheckOutDto checkout = orderService.checkout(1L, productOptionDtos);

        ShippingInfoDto shippingInfoDto = ShippingInfoDto.builder()
                .zipcode("10531")
                .address1("경기도 고양시 고양고양이")
                .address2("캣타워 304562층 33333호")
                .message("일회용 수저는 빼주세요")
                .name("삼순이")
                .clphNo("01033333333")
                .build();

        List<PaymentLineDto> paymentLineDtos = new ArrayList<>();
        
        PaymentLineDto paymentLineDto1 = new PaymentLineDto();
        paymentLineDto1.setPaymentType(PaymentType.COUPON);
        
        PaymentLineDto paymentLineDto2 = new PaymentLineDto();
        paymentLineDto2.setPaymentType(PaymentType.ACCOUNT);
        
        paymentLineDtos.add(paymentLineDto1);
        paymentLineDtos.add(paymentLineDto2);

        // when
        OrderDto order = orderService.order(checkout.getOrderId(), shippingInfoDto, paymentLineDtos);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.COMP);
        assertThat(order.getTotalPrice()).isEqualTo(30500 * 5 + 3350000 * 2);
        assertThat(order.getProductOptionDtos().get(0).getPrice()).isEqualTo(productOptionDto1.getPrice());
        assertThat(order.getProductOptionDtos().get(0).getQuantity()).isEqualTo(productOptionDto1.getQuantity());
        assertThat(order.getPaymentLineDtos().get(0).getPaymentType()).isEqualTo(paymentLineDto1.getPaymentType());
        assertThat(order.getShippingInfoDto().getName()).isEqualTo(shippingInfoDto.getName());
        assertThat(order.getShippingInfoDto().getClphNo()).isEqualTo(shippingInfoDto.getClphNo());
        assertThat(order.getShippingInfoDto().getAddress1()).isEqualTo(shippingInfoDto.getAddress1());
        assertThat(order.getShippingInfoDto().getAddress2()).isEqualTo(shippingInfoDto.getAddress2());
        assertThat(order.getShippingInfoDto().getZipcode()).isEqualTo(shippingInfoDto.getZipcode());
        assertThat(order.getShippingInfoDto().getMessage()).isEqualTo(shippingInfoDto.getMessage());
    }

    @Test
    public void orderServiceCancelTest() {
        //given
        ProductOptionDto productOptionDto1 = ProductOptionDto.builder()
                .productName("코카콜라제로리뉴얼")
                .optionName("355ml * 24")
                .price(30500)
                .quantity(5)
                .productOptionId(15L)
                .build();

        ProductOptionDto productOptionDto2 = ProductOptionDto.builder()
                .productName("맥북프로 16인치")
                .optionName("Ram 16GB, SSD 1TB, M1X Processor")
                .price(3350000)
                .quantity(2)
                .productOptionId(23L)
                .build();

        List<ProductOptionDto> productOptionDtos = new ArrayList<>();
        productOptionDtos.add(productOptionDto1);
        productOptionDtos.add(productOptionDto2);

        CheckOutDto checkout = orderService.checkout(1L, productOptionDtos);

        ShippingInfoDto shippingInfoDto = ShippingInfoDto.builder()
                .zipcode("10531")
                .address1("경기도 고양시 고양고양이")
                .address2("캣타워 304562층 33333호")
                .message("일회용 수저는 빼주세요")
                .name("삼순이")
                .clphNo("01033333333")
                .build();

        List<PaymentLineDto> paymentLineDtos = new ArrayList<>();

        PaymentLineDto paymentLineDto1 = new PaymentLineDto();
        paymentLineDto1.setPaymentType(PaymentType.COUPON);

        PaymentLineDto paymentLineDto2 = new PaymentLineDto();
        paymentLineDto2.setPaymentType(PaymentType.ACCOUNT);

        paymentLineDtos.add(paymentLineDto1);
        paymentLineDtos.add(paymentLineDto2);
        OrderDto order = orderService.order(checkout.getOrderId(), shippingInfoDto, paymentLineDtos);

        // when
        OrderDto cancel = orderService.cancel(order.getOrderId());

        // then
        assertThat(cancel.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    }
}
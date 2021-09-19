package study.commerceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.*;
import study.commerceservice.dto.*;
import study.commerceservice.repository.OrderRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final EntityManager em;
    private final OrderRepository orderRepository;

    @Transactional
    public CheckOutDto checkout(Long memberId, List<ProductOptionDto> productOptionDtos) {
        List<OrderLine> orderLines = new ArrayList<>();
        for(ProductOptionDto productOptionDto : productOptionDtos) {
            OrderLine orderLine = OrderLine.builder()
                    .price(productOptionDto.getPrice())
                    .quantity(productOptionDto.getQuantity())
                    .productOptionId((productOptionDto.getProductOptionId()))
                    .build();
            em.persist(orderLine);
            orderLines.add(orderLine);
        }

        Order preOrder = orderRepository.save(Order.createPreOrder(memberId, orderLines));

        CheckOutDto checkOutDto = new CheckOutDto();
        checkOutDto.setOrderId(preOrder.getId());
        checkOutDto.setProductOptionDtos(productOptionDtos);
        checkOutDto.setTotalPrice(preOrder.getTotalPrice());

        return checkOutDto;
    }

    @Transactional
    public OrderDto order(Long orderId
            , ShippingInfoDto shippingInfoDto
            , List<PaymentLineDto> paymentLineDtos
            , List<ProductOptionDto> productOptionDtos) {
        Order preOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        Address address = Address.builder()
                .zipCode(shippingInfoDto.getZipcode())
                .address1(shippingInfoDto.getAddress1())
                .address2(shippingInfoDto.getAddress2())
                .build();

        Receiver receiver = Receiver.builder()
                .name(shippingInfoDto.getName())
                .clphNo(shippingInfoDto.getClphNo())
                .build();

        ShippingInfo shippingInfo = ShippingInfo.builder()
                .message(shippingInfoDto.getMessage())
                .address(address)
                .receiver(receiver)
                .build();

        em.persist(shippingInfo);

        List<PaymentLine> paymentLines = new ArrayList<>();

        for(PaymentLineDto paymentLineDto : paymentLineDtos) {
            PaymentLine paymentLine = new PaymentLine(paymentLineDto.getPaymentType());
            em.persist(paymentLine);
            paymentLines.add(paymentLine);
        }

        Order order = Order.createOrder(preOrder, shippingInfo, paymentLines);

        OrderDto orderDto = new OrderDto();
        orderDto.setOrderId(order.getId());
        orderDto.setOrderNumber(order.getOrderNumber());
        orderDto.setOrderDate(order.getOrderDate());
        orderDto.setPaymentLineDtos(paymentLineDtos);
        orderDto.setShippingInfoDto(shippingInfoDto);
        orderDto.setProductOptionDtos(productOptionDtos);
        orderDto.setTotalPrice(order.getTotalPrice());

        return orderDto;
    }



}

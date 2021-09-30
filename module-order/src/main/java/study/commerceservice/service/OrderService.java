package study.commerceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.Order;
import study.commerceservice.domain.OrderLine;
import study.commerceservice.domain.PaymentLine;
import study.commerceservice.domain.ShippingInfo;
import study.commerceservice.controller.dto.*;
import study.commerceservice.repository.OrderRepository;
import study.commerceservice.service.assembler.OrderAssembler;
import study.commerceservice.service.assembler.OrderLineAssembler;
import study.commerceservice.service.assembler.PaymentLineAssembler;
import study.commerceservice.service.assembler.ShippingInfoAssembler;

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
            OrderLine orderLine = OrderLineAssembler.toEntity(productOptionDto);
            em.persist(orderLine);
            orderLines.add(orderLine);
        }

        Order preOrder = orderRepository.save(Order.createPreOrder(memberId, orderLines));
        return OrderAssembler.toCheckOutDto(preOrder);
    }

    @Transactional
    public OrderDto order(Long orderId, ShippingInfoDto shippingInfoDto, List<PaymentLineDto> paymentLineDtos) {
        Order preOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        ShippingInfo shippingInfo = ShippingInfoAssembler.toEntity(shippingInfoDto);
        em.persist(shippingInfo);

        List<PaymentLine> paymentLines = new ArrayList<>();
        for(PaymentLineDto paymentLineDto : paymentLineDtos) {
            PaymentLine paymentLine = PaymentLineAssembler.toEntity(paymentLineDto);
            em.persist(paymentLine);
            paymentLines.add(paymentLine);
        }

        Order order = Order.createOrder(preOrder, shippingInfo, paymentLines);
        return OrderAssembler.toOrderDto(order);
    }

    @Transactional
    public OrderDto cancel(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        order.cancel(order);

        // TODO(product 수량 +)
        // TODO(Payment 환불처리)

        return OrderAssembler.toOrderDto(order);
    }

}

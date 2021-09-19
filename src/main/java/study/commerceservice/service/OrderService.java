package study.commerceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.Order;
import study.commerceservice.domain.order.OrderLine;
import study.commerceservice.dto.CheckOutDto;
import study.commerceservice.dto.PaymentLineDto;
import study.commerceservice.dto.PreOrderDto;
import study.commerceservice.dto.ShippingInfoDto;
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
    public CheckOutDto checkout(Long memberId, List<PreOrderDto> preOrderDtos) {
        List<OrderLine> orderLines = new ArrayList<>();
        for(PreOrderDto preOrderDto : preOrderDtos) {
            OrderLine orderLine = OrderLine.builder()
                    .price(preOrderDto.getPrice())
                    .quantity(preOrderDto.getQuantity())
                    .productOptionId((preOrderDto.getProductOptionId()))
                    .build();
            em.persist(orderLine);
            orderLines.add(orderLine);
        }

        Order preOrder = orderRepository.save(Order.createPreOrder(memberId, orderLines));

        CheckOutDto checkOutDto = new CheckOutDto();
        checkOutDto.setOrderId(preOrder.getId());
        checkOutDto.setPreOrderDtos(preOrderDtos);
        checkOutDto.setTotalPrice(preOrder.getTotalPrice());

        return checkOutDto;
    }

    @Transactional
    public Order order(ShippingInfoDto shippingInfoDto, List<PaymentLineDto> paymentLineDtos) {


        return null;
    }



}

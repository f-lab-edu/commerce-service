package study.commerceservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDto {

    private Long orderId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private List<ProductOptionDto> productOptionDtos;
    private List<PaymentLineDto> paymentLineDtos;
    private ShippingInfoDto shippingInfoDto;
    private long totalPrice;
}

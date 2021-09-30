package study.commerceservice.controller.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CheckOutDto {
    private Long orderId;
    private List<ProductOptionDto> productOptionDtos;
    private Long totalPrice;
}

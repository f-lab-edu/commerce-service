package study.commerceservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class CheckOutDto {

    private Long orderId;
    private List<PreOrderDto> preOrderDtos;
    private Long totalPrice;
}

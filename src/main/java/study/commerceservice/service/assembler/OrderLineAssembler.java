package study.commerceservice.service.assembler;

import study.commerceservice.domain.order.OrderLine;
import study.commerceservice.dto.ProductOptionDto;

public class OrderLineAssembler {

    public static OrderLine toEntity(ProductOptionDto productOptionDto) {
        return OrderLine.builder()
                .quantity(productOptionDto.getQuantity())
                .price(productOptionDto.getPrice())
                .productOptionId(productOptionDto.getProductOptionId())
                .build();
    }

    public static ProductOptionDto toDto(OrderLine orderLine) {
        // TODO: Product Aggregate에서 OptionName, ProductName 조회 - hyemin
        String productName = "";
        String optionName = "";

        return ProductOptionDto.builder()
                .quantity(orderLine.getQuantity())
                .price(orderLine.getPrice())
                .productOptionId(orderLine.getProductOptionId())
                .productName(productName)
                .optionName(optionName)
                .build();
    }

}

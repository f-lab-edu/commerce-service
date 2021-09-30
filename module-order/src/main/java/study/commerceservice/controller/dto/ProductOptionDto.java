package study.commerceservice.controller.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ProductOptionDto {

    @NotNull
    private String productName;

    @NotNull
    private String optionName;

    @NotNull
    private Long productOptionId;

    private int quantity;
    private long price;
}

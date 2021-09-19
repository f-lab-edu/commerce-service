package study.commerceservice.dto;

import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class PreOrderDto {

    @NotNull
    private String productName;

    @NotNull
    private String optionName;

    @NotNull
    private Long productOptionId;

    private int quantity;
    private long price;
}

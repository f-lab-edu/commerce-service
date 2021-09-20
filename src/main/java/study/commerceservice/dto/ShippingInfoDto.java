package study.commerceservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
public class ShippingInfoDto {

    private String message;
    @NotEmpty
    private String zipcode;
    @NotEmpty
    private String address1;
    @NotEmpty
    private String address2;
    @NotEmpty
    private String name;
    @NotEmpty
    private String clphNo;
}

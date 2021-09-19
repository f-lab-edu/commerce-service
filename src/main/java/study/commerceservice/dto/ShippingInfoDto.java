package study.commerceservice.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

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

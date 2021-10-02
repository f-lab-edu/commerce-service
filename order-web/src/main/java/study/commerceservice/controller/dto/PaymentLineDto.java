package study.commerceservice.controller.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import study.commerceservice.domain.PaymentType;

@Data
public class PaymentLineDto {

    @NotNull
    private PaymentType paymentType;
}

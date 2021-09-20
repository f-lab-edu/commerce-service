package study.commerceservice.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import study.commerceservice.domain.order.PaymentType;

@Data
public class PaymentLineDto {

    @NotNull
    private PaymentType paymentType;
}

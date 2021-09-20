package study.commerceservice.service.assembler;

import study.commerceservice.domain.order.PaymentLine;
import study.commerceservice.dto.PaymentLineDto;

public class PaymentLineAssembler {
    public static PaymentLine toEntity(PaymentLineDto paymentLineDto) {
        return new PaymentLine(paymentLineDto.getPaymentType());
    }

    public static PaymentLineDto toDto(PaymentLine paymentLine) {
        PaymentLineDto paymentLineDto = new PaymentLineDto();
        paymentLineDto.setPaymentType(paymentLine.getPaymentType());
        return paymentLineDto;
    }
}

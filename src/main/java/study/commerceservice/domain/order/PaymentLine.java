package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentLine {

    @Id
    @GeneratedValue
    @Column(name = "payment_line_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public PaymentLine(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}

package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {

    @Id
    @GeneratedValue
    @Column(name = "payment_info_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    public PaymentInfo(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}

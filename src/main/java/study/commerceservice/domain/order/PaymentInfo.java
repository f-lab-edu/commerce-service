package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaymentInfo {

    @Id
    @GeneratedValue
    @Column(name = "payment_info_id")
    private Long id;

    private PaymentType paymentType;

    public PaymentInfo(PaymentType paymentType) {
        this.paymentType = paymentType;
    }
}

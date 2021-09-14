package study.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PaymentInfo {

    @Id
    @GeneratedValue
    @Column(name = "paymentinfo_id")
    private Long id;

    private PaymentType paymentType;
}

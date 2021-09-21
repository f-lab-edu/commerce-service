package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.commerceservice.common.CommonTimeEntity;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo extends CommonTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "shipping_info_id")
    private Long id;

    private String message;

    @Embedded
    private Address address;

    @Embedded
    private Receiver receiver;

    @Builder
    public ShippingInfo(String message, Address address, Receiver receiver) {
        this.message = message;
        this.address = address;
        this.receiver = receiver;
    }
}

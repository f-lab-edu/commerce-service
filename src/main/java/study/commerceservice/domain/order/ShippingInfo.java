package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShippingInfo {

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

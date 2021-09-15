package study.domain.order;

import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
public class ShippingInfo {

    @Id
    @GeneratedValue
    @Column(name = "shippinginfo_id")
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

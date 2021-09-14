package study.domain.order;

import javax.persistence.*;

@Entity
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
}

package study.domain.order;

import lombok.Builder;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderer_id")
    private Orderer orderer;

    private LocalDateTime orderDate;
    private long totalPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private ShippingInfo shippingInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private PaymentInfo paymentInfo;

    private OrderStatus orderStatus;


}

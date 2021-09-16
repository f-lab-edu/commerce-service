package study.commerceservice.domain.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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
    @JoinColumn(name = "shipping_info_id")
    private ShippingInfo shippingInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_info_id")
    private PaymentInfo paymentInfo;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}

package study.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public Order(String orderNumber
            , Orderer orderer
            , LocalDateTime orderDate
            , long totalPrice
            , List<OrderLine> orderLines
            , ShippingInfo shippingInfo
            , PaymentInfo paymentInfo
            , OrderStatus orderStatus) {
        this.orderNumber = orderNumber;
        this.orderer = orderer;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.orderLines = orderLines;
        this.shippingInfo = shippingInfo;
        this.paymentInfo = paymentInfo;
        this.orderStatus = orderStatus;
    }
}

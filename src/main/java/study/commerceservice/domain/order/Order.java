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
    private Long memberId;
    private LocalDateTime orderDate;
    private long totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_line_id")
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_info_id")
    private ShippingInfo shippingInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_line_id")
    private List<PaymentLine> paymentLines;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

}

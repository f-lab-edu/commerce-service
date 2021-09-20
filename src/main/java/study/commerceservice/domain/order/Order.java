package study.commerceservice.domain.order;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
@Entity
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    private String orderNumber;
    private Long memberId;
    private LocalDateTime orderDate;
    private LocalDateTime cancelDate;
    private LocalDateTime modDate;
    private long totalPrice;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_line_id")
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_info_id")
    private ShippingInfo shippingInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "payment_line_id")
    private List<PaymentLine> paymentLines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    public static Order createPreOrder(Long memderId, List<OrderLine> orderLines) {
        Order order = new Order();
        order.setMemberId(memderId);
        order.setModDate(LocalDateTime.now());
        order.setOrderLines(orderLines);
        order.setOrderStatus(OrderStatus.PRE);
        orderLines.forEach(rec -> order.setTotalPrice(order.getTotalPrice() + rec.getTotalPrice()));

        return order;
    }

    public static Order createOrder(Order order, ShippingInfo shippingInfo, List<PaymentLine> paymentLines) {
        LocalDateTime now = LocalDateTime.now();
        order.setOrderNumber(now.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                +"-"+order.getMemberId()
                +"-"+order.getId());
        order.setOrderDate(now);
        order.setOrderStatus(OrderStatus.COMP);
        order.setShippingInfo(shippingInfo);
        order.setPaymentLines(paymentLines);

        return order;
    }

    public void cancel(Order order) {
        //TODO (DeliveryStatus 체크)

        order.setOrderStatus(OrderStatus.CANCEL);
        order.setModDate(LocalDateTime.now());
    }
}

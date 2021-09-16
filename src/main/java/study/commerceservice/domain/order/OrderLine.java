package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Id
    @GeneratedValue
    @Column(name = "order_line_id")
    private Long id;
    private int quantity;
    private long price;
    private Long productOptionId;

    @Builder
    public OrderLine(int quantity, long price, Long productOptionId) {
        this.quantity = quantity;
        this.price = price;
        this.productOptionId = productOptionId;
    }
}

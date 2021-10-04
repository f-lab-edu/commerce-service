package study.commerceservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.commerceservice.domain.common.CommonTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine extends CommonTimeEntity {

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

    public long getTotalPrice() {
        return price * quantity;
    }
}

package study.commerceservice.domain.order;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import study.commerceservice.domain.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderLine {

    @Id
    @GeneratedValue
    @Column(name = "order_line_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;
    private long price;

    @OneToMany(mappedBy = "orderLine", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    @Builder
    public OrderLine(Product product, int quantity, long price, List<Option> options) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.options = options;
    }
}

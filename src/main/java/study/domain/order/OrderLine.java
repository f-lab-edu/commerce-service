package study.domain.order;

import lombok.Builder;
import lombok.NoArgsConstructor;
import study.domain.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
public class OrderLine {

    @Id
    @GeneratedValue
    @Column(name = "orderline_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
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

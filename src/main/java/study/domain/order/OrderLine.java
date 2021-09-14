package study.domain.order;

import study.domain.product.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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
}

package study.commerceservice.domain.product;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.commerceservice.domain.common.CommonTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Option extends CommonTimeEntity {

    @Id @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    private long optionPrice;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    @Builder
    public Option(long optionPrice, String description, Stock stock) {
        this.optionPrice = optionPrice;
        this.description = description;
        this.stock = stock;
    }

    //연관관계 메서드
    public void setProduct(Product product) {
        this.product = product;
    }
}

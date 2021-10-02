package study.commerceservice.domain.product;

import lombok.*;
import study.commerceservice.domain.common.CommonTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends CommonTimeEntity {

    @Id @GeneratedValue
    @Column(name = "product_id")
    private Long id;

    private String name;
    private long price;
    private Long sellerId;
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Option> options = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_image_id")
    private List<ProductImage> productImages = new ArrayList<>();

    @Builder
    public Product(String name, long price, Long sellerId, String description, List<Option> options, List<ProductImage> productImages) {
        this.name = name;
        this.price = price;
        this.sellerId = sellerId;
        this.description = description;
        this.productImages = productImages;
        addOptions(options);
    }

    public void addOptions(List<Option> options) {
        for(Option option : options) {
            this.options.add(option);
            option.setProduct(this);
        }
    }

}

package study.commerceservice.domain.product;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductImage {

    @Id @GeneratedValue
    @Column(name = "product_image_id")
    private Long id;

    private String imgUrl;
    private int ord;

    @Enumerated(EnumType.STRING)
    private ProductImageType productImageType;

    @Builder
    public ProductImage(String imgUrl, int ord, ProductImageType productImageType) {
        this.imgUrl = imgUrl;
        this.ord = ord;
        this.productImageType = productImageType;
    }
}

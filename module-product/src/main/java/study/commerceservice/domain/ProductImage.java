package study.commerceservice.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.commerceservice.domain.common.CommonTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductImage extends CommonTimeEntity {

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

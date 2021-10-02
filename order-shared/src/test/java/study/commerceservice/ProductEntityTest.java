package study.commerceservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.product.*;
import study.commerceservice.repository.ProductRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional(readOnly = true)
public class ProductEntityTest {

    @Autowired EntityManager em;
    @Autowired
    ProductRepository productRepository;

    @Test
    public void createProductTest() {
        List<Option> options = new ArrayList<>();

        Option option1 = Option.builder()
                .description("코카콜라 제로 355ml * 24")
                .optionPrice(20500)
                .stock(new Stock(23))
                .build();

        Option option2 = Option.builder()
                .description("코카콜라 제로 355ml * 48")
                .optionPrice(40000)
                .stock(new Stock(51))
                .build();

        Option option3 = Option.builder()
                .description("코카콜라 제로 240ml * 24")
                .optionPrice(0)
                .stock(new Stock(15))

                .build();

        Option option4 = Option.builder()
                .description("코카콜라 제로 240ml * 48")
                .optionPrice(14500)
                .stock(new Stock(18))
                .build();

        em.persist(option1);
        em.persist(option2);
        em.persist(option3);
        em.persist(option4);

        options.add(option1);
        options.add(option2);
        options.add(option3);
        options.add(option4);

        List<ProductImage> productImages = new ArrayList<>();

        ProductImage productImage1 = ProductImage.builder()
                .imgUrl("https://commerce-service.com/images/375")
                .ord(1)
                .productImageType(ProductImageType.NORMAL)
                .build();

        ProductImage productImage2 = ProductImage.builder()
                .imgUrl("https://commerce-service.com/images/742")
                .ord(2)
                .productImageType(ProductImageType.REPRESENT)
                .build();

        ProductImage productImage3 = ProductImage.builder()
                .imgUrl("https://commerce-service.com/images/689")
                .ord(3)
                .productImageType(ProductImageType.NORMAL)
                .build();

        em.persist(productImage1);
        em.persist(productImage2);
        em.persist(productImage3);

        productImages.add(productImage1);
        productImages.add(productImage2);
        productImages.add(productImage3);

        Product product = Product.builder()
                .name("코카콜라 355ml, 240ml 최저가")
                .price(14500)
                .description("코카콜라 제로 355ml * 24, 355ml * 48 , 240ml * 24, 240ml * 48")
                .sellerId(2L)
                .options(options)
                .productImages(productImages)
                .build();

        Product save = productRepository.save(product);

        Product findProduct = productRepository.findById(save.getId()).get();

        assertThat(findProduct.getName()).isEqualTo(product.getName());
        assertThat(findProduct.getOptions().get(0).getProduct()).isEqualTo(findProduct);
        assertThat(findProduct.getOptions().get(0).getOptionPrice()).isEqualTo(option1.getOptionPrice());
        assertThat(findProduct.getOptions().get(3).getOptionPrice()).isEqualTo(option4.getOptionPrice());
        assertThat(findProduct.getProductImages().get(1).getProductImageType()).isEqualTo(productImage2.getProductImageType());

    }
}

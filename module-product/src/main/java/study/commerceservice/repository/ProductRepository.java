package study.commerceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.commerceservice.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}

package study.commerceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.commerceservice.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

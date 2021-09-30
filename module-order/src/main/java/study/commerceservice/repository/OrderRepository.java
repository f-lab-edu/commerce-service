package study.commerceservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.commerceservice.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

package study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.domain.order.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

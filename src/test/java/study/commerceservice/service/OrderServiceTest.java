package study.commerceservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.domain.order.Orderer;
import study.commerceservice.repository.OrderRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {

    OrderRepository orderRepository;


    @Test
    public void orderTest() {

    }
}
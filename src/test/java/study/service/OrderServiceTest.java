package study.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.domain.order.Orderer;
import study.repository.OrderRepository;

@SpringBootTest
@Transactional
class OrderServiceTest {

    OrderRepository orderRepository;


    @Test
    public void orderTest() {

    }
}
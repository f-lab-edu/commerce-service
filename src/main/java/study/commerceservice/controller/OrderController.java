package study.commerceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import study.commerceservice.repository.OrderRepository;
import study.commerceservice.service.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;


}

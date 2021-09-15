package study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import study.service.OrderService;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
}

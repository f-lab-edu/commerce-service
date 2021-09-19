package study.commerceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.commerceservice.domain.order.Order;
import study.commerceservice.dto.PaymentLineDto;
import study.commerceservice.dto.PreOrderDto;
import study.commerceservice.dto.ShippingInfoDto;
import study.commerceservice.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam("memberId") Long memberId
            , @Valid @ModelAttribute("preOrders") List<PreOrderDto> preOrderDtos
            , Model model) {
        model.addAttribute("checkout", orderService.checkout(memberId, preOrderDtos));

        return "redirect:/checkout/checkoutForm";
    }

    @PostMapping("/order")
    public String order(@Valid @ModelAttribute("shippingInfo") ShippingInfoDto shippingInfoDto,
                        @Valid @ModelAttribute("paymentLines") List<PaymentLineDto> paymentLineDtos) {
        orderService.order(shippingInfoDto, paymentLineDtos);
        return "redirect:/order/orderForm";
    }
}

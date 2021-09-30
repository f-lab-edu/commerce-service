package study.commerceservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import study.commerceservice.controller.dto.PaymentLineDto;
import study.commerceservice.controller.dto.ProductOptionDto;
import study.commerceservice.controller.dto.ShippingInfoDto;
import study.commerceservice.service.OrderService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public String checkout(@RequestParam("memberId") Long memberId
            , @Valid @ModelAttribute("productOptions") List<ProductOptionDto> productOptionDtos
            , Model model) {
        model.addAttribute("checkout", orderService.checkout(memberId, productOptionDtos));

        return "redirect:/checkout/checkoutForm";
    }

    @PostMapping("/order")
    public String order(@RequestParam("orderId") Long orderId,
                        @Valid @ModelAttribute("shippingInfo") ShippingInfoDto shippingInfoDto,
                        @Valid @ModelAttribute("paymentLines") List<PaymentLineDto> paymentLineDtos) {
        orderService.order(orderId, shippingInfoDto, paymentLineDtos);
        return "redirect:/order/orderForm";
    }

    @PostMapping("/orderCencel")
    public String cancel(@RequestParam("orderId") Long orderId) {
        orderService.cancel(orderId);
        return "redirect:/order/cancelForm";
    }
}

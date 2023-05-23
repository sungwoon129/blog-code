package com.blog.jpashop.order.presentation;

import com.blog.jpashop.item.application.ItemService;
import com.blog.jpashop.member.application.MemberService;
import com.blog.jpashop.order.application.OrderService;
import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.domain.OrderStatus;
import com.blog.jpashop.order.presentation.model.OrderRequest;
import com.blog.jpashop.order.presentation.model.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders")
    public String list(Model model,OrderSearch orderSearch) {
        model.addAttribute("orders",orderService.findOrders(orderSearch));
        model.addAttribute("orderStatus", Arrays.stream(OrderStatus.values()).collect(Collectors.toList()));
        return "/orders/orderList";
    }

    @GetMapping("/orders/new")
    public String placeOrderPage(Model model) {
        model.addAttribute("members",memberService.findMembers());
        model.addAttribute("items",itemService.findItems());
        return "/orders/placeOrderForm";
    }

    @PostMapping("/orders/new")
    public String placeOrder(OrderRequest orderRequest) {
        orderService.order(orderRequest.getMemberId(), orderRequest.getItemId(), orderRequest.getCount());
        return "redirect:/orders";
    }

    @PutMapping("/orders/{orderId}/cancel")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId) {
        orderService.cancelOrder(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

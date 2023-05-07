package com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation;

import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.application.PlaceOrderService;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderNo;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private PlaceOrderService placeOrderService;

    public OrderController(PlaceOrderService placeOrderService) {
        this.placeOrderService = placeOrderService;
    }


    @PostMapping(value = "/orders/order", consumes = "application/json", produces = "application/json")
    public String order(@RequestBody OrderRequest orderRequest) {
        orderRequest.setOrdererMemberId(MemberId.of(orderRequest.getOrdererMemberId().getId()));
        try {
            OrderNo orderNo = placeOrderService.placeOrder(orderRequest);
            return orderNo.getNumber();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}

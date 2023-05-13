package com.blog.spring_event_with_slack.spring_event_with_slack.order.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderPlacedEvent {

    private ShippingInfo shippingInfo;
    private List<OrderLine> orderLines;
    private LocalDateTime orderDate;


    protected OrderPlacedEvent() {}

    public OrderPlacedEvent(ShippingInfo shippingInfo, List<OrderLine> orderLines, LocalDateTime orderDate) {
        this.shippingInfo = shippingInfo;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
    }
}

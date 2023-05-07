package com.blog.spring_event_with_slack.spring_event_with_slack.order.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderPlacedEvent {

    private Orderer orderer;
    private List<OrderLine> orderLines;
    private LocalDateTime orderDate;


    protected OrderPlacedEvent() {}

    public OrderPlacedEvent(Orderer orderer, List<OrderLine> orderLines, LocalDateTime orderDate) {
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.orderDate = orderDate;
    }



}

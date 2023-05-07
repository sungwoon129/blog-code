package com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OrderProduct {
    private String productId;
    private int quantity;

    public OrderProduct(String productId, int quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }
}

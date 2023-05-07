package com.blog.spring_event_with_slack.spring_event_with_slack.catalog.presentation.model;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class ProductRequest {

    private String name;
    private Money price;

    public ProductRequest() {}

    public ProductRequest(String name, Money price) {
        this.name = name;
        this.price = price;
    }
}

package com.blog.spring_event_with_slack.spring_event_with_slack.order.domain;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.Product;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.jpa.MoneyConverter;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Money;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Embeddable
public class OrderLine {

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;
    @Column(name = "quantity")
    private int quantity;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amounts")
    private Money amounts;

    protected OrderLine() {}

    public OrderLine(Product product, Money price, int quantity) {
        this.product = product;
        this.price = price;
        this.quantity = quantity;
        this.amounts = calculateAmounts();
    }

    private Money calculateAmounts() {
        return price.multiply(quantity);
    }

}

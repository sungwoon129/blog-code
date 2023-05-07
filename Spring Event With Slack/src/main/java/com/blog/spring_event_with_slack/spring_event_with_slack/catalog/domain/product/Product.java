package com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product;

import com.blog.spring_event_with_slack.spring_event_with_slack.common.jpa.MoneyConverter;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Money;

import javax.persistence.Convert;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class Product {
    @EmbeddedId
    private ProductId id;

    private String name;

    @Convert(converter = MoneyConverter.class)
    private Money price;

    protected Product() {
    }

    public Product(ProductId id, String name, Money price) {
        this.id = id;
        this.name = name;
        this.price = price;

    }

    public ProductId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

}
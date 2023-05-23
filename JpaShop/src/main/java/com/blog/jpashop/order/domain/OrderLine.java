package com.blog.jpashop.order.domain;

import com.blog.jpashop.common.model.BaseEntity;
import com.blog.jpashop.item.domain.Item;


import jakarta.persistence.*;

@Entity
@Table(name = "ORDER_LINE")
public class OrderLine extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "ORDER_LINE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;


    public static OrderLine createOrderLine(Item item, int orderPrice, int count) {
        OrderLine orderLine = new OrderLine();
        orderLine.setItem(item);
        orderLine.setOrderPrice(orderPrice);
        orderLine.setCount(count);

        item.removeStock(count);
        return orderLine;
    }

    public void cancel() {
        getItem().addStock(count);
    }

    public int getTotalPrice() {
        return getOrderPrice() * count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }


}

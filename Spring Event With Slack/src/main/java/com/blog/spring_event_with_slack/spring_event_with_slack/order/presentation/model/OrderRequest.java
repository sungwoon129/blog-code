package com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model;

import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.ShippingInfo;

import java.util.List;

public class OrderRequest {
    private List<OrderProduct> orderProducts;
    private MemberId ordererMemberId;
    private ShippingInfo shippingInfo;

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public MemberId getOrdererMemberId() {
        return ordererMemberId;
    }

    public void setOrdererMemberId(MemberId ordererMemberId) {
        this.ordererMemberId = ordererMemberId;
    }

    public ShippingInfo getShippingInfo() {
        return shippingInfo;
    }

    public void setShippingInfo(ShippingInfo shippingInfo) {
        this.shippingInfo = shippingInfo;
    }
}

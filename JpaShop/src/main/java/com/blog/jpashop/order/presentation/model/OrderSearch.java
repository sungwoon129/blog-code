package com.blog.jpashop.order.presentation.model;

import com.blog.jpashop.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}

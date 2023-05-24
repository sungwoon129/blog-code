package com.blog.jpashop.order.presentation.model;

import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import static com.blog.jpashop.order.domain.OrderSpec.memberNameLike;
import static com.blog.jpashop.order.domain.OrderSpec.orderStatusEquals;
import static org.springframework.data.jpa.domain.Specification.where;

@Setter
@Getter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;

    public Specification<Order> toSpecification() {
        return where(memberNameLike(memberName)).and(orderStatusEquals(orderStatus));
    }
}

package com.blog.jpashop.order.infrastructure;

import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.presentation.model.OrderSearch;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface CustomOrderRepository {
    List<Order> search(OrderSearch orderSearch);
}

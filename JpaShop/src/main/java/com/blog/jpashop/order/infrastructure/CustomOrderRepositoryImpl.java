package com.blog.jpashop.order.infrastructure;

import com.blog.jpashop.member.domain.QMember;
import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.domain.QOrder;
import com.blog.jpashop.order.presentation.model.OrderSearch;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
public class CustomOrderRepositoryImpl extends QuerydslRepositorySupport implements CustomOrderRepository {
    public CustomOrderRepositoryImpl() {
        super(Order.class);
    }

    @Override
    public List<Order> search(OrderSearch orderSearch) {
        QOrder order = QOrder.order;
        QMember member = QMember.member;

        JPQLQuery<Order> query = from(order);

        if(StringUtils.hasText(orderSearch.getMemberName())) {
            query.leftJoin(order.member,member).where(member.name.contains(orderSearch.getMemberName()));
        }

        if(orderSearch.getOrderStatus() != null) {
            query.where(order.status.eq(orderSearch.getOrderStatus()));
        }

        return query.fetch();
    }
}

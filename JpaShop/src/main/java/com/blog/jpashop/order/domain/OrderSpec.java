package com.blog.jpashop.order.domain;

import com.blog.jpashop.member.domain.Member;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class OrderSpec {

    public static Specification<Order> memberNameLike(final String memberName) {
        return (root, query, criteriaBuilder) -> {

            if(!StringUtils.hasText(memberName)) return null;

            Join<Order, Member> m = root.join("Member", JoinType.INNER);
            return criteriaBuilder.like(m.<String>get("name"),"%" + memberName + "%");
        };
    }

    public static Specification<Order> orderStatusEquals(final OrderStatus orderStatus) {
        return ((root, query, criteriaBuilder) -> {

            if(orderStatus == null) return null;

           return criteriaBuilder.equal(root.get("status"),orderStatus);

        });
    }
}

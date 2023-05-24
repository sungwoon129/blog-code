package com.blog.jpashop.order.application;

import com.blog.jpashop.item.application.ItemService;
import com.blog.jpashop.item.domain.Item;
import com.blog.jpashop.member.application.MemberService;
import com.blog.jpashop.member.domain.Member;
import com.blog.jpashop.order.domain.Delivery;
import com.blog.jpashop.order.domain.Order;
import com.blog.jpashop.order.domain.OrderLine;
import com.blog.jpashop.order.infrastructure.CustomOrderRepository;
import com.blog.jpashop.order.infrastructure.OrderRepository;
import com.blog.jpashop.order.presentation.model.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomOrderRepository customOrderRepository;
    private final MemberService memberService;
    private final ItemService itemService;


    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberService.findById(memberId);
        Item item = itemService.findById(itemId);

        Delivery delivery = new Delivery(member.getAddress());
        OrderLine orderLine = OrderLine.createOrderLine(item,item.getPrice(),count);
        Order order = Order.createOrder(member,delivery,orderLine);

        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow();
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        /**
         * Criteria Specification
         */
        //return orderRepository.findAll(orderSearch.toSpecification());

        /**
         * queryDSL
         */
        return customOrderRepository.search(orderSearch);
    }


}

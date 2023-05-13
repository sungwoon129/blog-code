package com.blog.spring_event_with_slack.spring_event_with_slack.order.application;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.Product;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductRepository;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.*;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure.OrderRepository;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure.AlarmService;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model.OrderProduct;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model.OrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PlaceOrderService {

    private OrderRepository orderRepository;
    private OrdererService ordererService;
    private ProductRepository productRepository;
    private AlarmService slackAlarmService;

    public PlaceOrderService(OrderRepository orderRepository,
                             OrdererService ordererService,
                             ProductRepository productRepository,
                             AlarmService slackAlarmService
    ) {
        this.orderRepository = orderRepository;
        this.ordererService = ordererService;
        this.productRepository = productRepository;
        this.slackAlarmService = slackAlarmService;
    }

    @Transactional
    public OrderNo placeOrder(OrderRequest orderRequest) {
        List<OrderLine> orderLines = new ArrayList<>();
        for (OrderProduct op : orderRequest.getOrderProducts()) {
            Optional<Product> productOpt = productRepository.findById(new ProductId(op.getProductId()));
            Product product = productOpt.orElseThrow(() -> new NoSuchElementException(op.getProductId() + " no exist."));
            orderLines.add(new OrderLine(product, product.getPrice(), op.getQuantity()));
        }
        OrderNo orderNo = orderRepository.nextOrderNo();
        Orderer orderer = ordererService.createOrderer(orderRequest.getOrdererMemberId());
        Order order = new Order(orderNo, orderer, orderRequest.getShippingInfo(), orderLines);
        orderRepository.save(order);
        return orderNo;
    }
}

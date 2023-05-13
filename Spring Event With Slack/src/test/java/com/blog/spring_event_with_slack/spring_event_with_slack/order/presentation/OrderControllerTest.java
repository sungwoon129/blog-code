package com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.Product;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductRepository;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Address;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Money;
import com.blog.spring_event_with_slack.spring_event_with_slack.member.MemberId;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.application.PlaceOrderService;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.Order;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderNo;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.Receiver;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.ShippingInfo;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.infrastructure.OrderRepository;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model.OrderProduct;
import com.blog.spring_event_with_slack.spring_event_with_slack.order.presentation.model.OrderRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.autoconfigure.AutoConfigurationPackages.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    PlaceOrderService placeOrderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mvc;


    @BeforeEach
    public void setup() {
        ProductId productId = new ProductId("1");
        Product sampleProduct = new Product(productId,"New jeans - hype boy",new Money(10000));
        productRepository.save(sampleProduct);
    }

    @Test
    public void 상품을_등록한다() {
        ProductId productId = new ProductId("1");
        Product sampleProduct = new Product(productId,"New jeans - hype boy",new Money(10000));
        productRepository.save(sampleProduct);

        Optional<Product> product = productRepository.findById(productId);
        assertThat(product.isPresent() ? product.get().getId() : null).isEqualTo(sampleProduct.getId());
    }

    @Test
    void 주문을_넣고_슬랙알람이벤트가_발생한다() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        MemberId id = MemberId.of("tester");
        OrderProduct orderProduct = new OrderProduct("1",2);
        List<OrderProduct> orderProducts = List.of(orderProduct);
        Receiver receiver = new Receiver("swy","01012345678");

        ShippingInfo shippingInfo = new ShippingInfo(new Address("123-123","korea","home"),"배송합니다",receiver);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrdererMemberId(id);
        orderRequest.setOrderProducts(orderProducts);
        orderRequest.setShippingInfo(shippingInfo);

        String url = "http://localhost:" + port + "/" + "orders/order";

        String data = objectMapper.writeValueAsString(orderRequest);

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(data)
        ).andExpect(status().isOk());
    }
}
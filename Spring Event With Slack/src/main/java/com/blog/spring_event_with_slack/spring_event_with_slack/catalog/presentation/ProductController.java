package com.blog.spring_event_with_slack.spring_event_with_slack.catalog.presentation;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.application.ProductService;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.presentation.model.ProductRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(value = "/products/product", consumes = "application/json", produces = "application/json")
    public String registerProduct(@RequestBody ProductRequest productRequest) throws JsonProcessingException {

        ProductId registeredProductId = productService.register(productRequest);

        return registeredProductId.getId();

    }
}

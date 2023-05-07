package com.blog.spring_event_with_slack.spring_event_with_slack.catalog.application;

import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.Product;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductId;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product.ProductRepository;
import com.blog.spring_event_with_slack.spring_event_with_slack.catalog.presentation.model.ProductRequest;
import com.blog.spring_event_with_slack.spring_event_with_slack.common.model.Money;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductId register(ProductRequest productRequest) {
        ProductId productId = productRepository.nextProductId();
        Product product = new Product(productId,productRequest.getName(),productRequest.getPrice());
        productRepository.save(product);
        return productId;
    }
}

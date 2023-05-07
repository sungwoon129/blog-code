package com.blog.spring_event_with_slack.spring_event_with_slack.catalog.domain.product;

import com.blog.spring_event_with_slack.spring_event_with_slack.order.domain.OrderNo;
import org.springframework.data.repository.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public interface ProductRepository extends Repository<Product, ProductId> {
    void save(Product product);

    Optional<Product> findById(ProductId id);

    void flush();

    default ProductId nextProductId() {
        int randomNo = ThreadLocalRandom.current().nextInt(900000) + 100000;
        String number = String.format("%tY%<tm%<td%<tH-%d", new Date(), randomNo);
        return new ProductId(number);
    }
}

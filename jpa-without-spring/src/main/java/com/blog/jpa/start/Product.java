package com.blog.jpa.start;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @Column(name = "product_id")
    private String id;

    @Column(name = "product_name")
    private String name;

    @OneToMany(mappedBy = "product")
    private List<Order> orders;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }


}

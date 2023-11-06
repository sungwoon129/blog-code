package com.blog.prototype.framework;

import java.util.HashMap;

public class Manager {

    private final HashMap<String, Product> showcase = new HashMap<>();
    public void register(String name, Product proto){
        showcase.put(name,proto);
    }
    public Product create(String protoName){
        Product p = showcase.get(protoName);
        return p.createClone();
    }
}

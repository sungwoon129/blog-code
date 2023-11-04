package com.blog.factory_method;

public class ConcreteFactory implements AbstractFactory {
    @Override
    public ProductInterface createProduct() {
        return new ConcreteProduct();
    }

    private static class ConcreteProduct implements ProductInterface {
        @Override
        public void someFunc() {
            System.out.println("productA");

        }
    }
}

package com.blog.factory_method;

public interface AbstractFactory {

    default ProductInterface makeSomeOperation() {

        ProductInterface productInterface = createProduct();
        productInterface.someFunc();

        return productInterface;
    }

    ProductInterface createProduct();
}

package com.blog.template_method;

public class Cash extends Payment {
    @Override
    void payByMethod() {
        System.out.println("현금 결제");
    }
}

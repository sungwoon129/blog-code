package com.blog.template_method;

public class Point extends Payment{
    @Override
    void payByMethod() {
        System.out.println("포인트 결제");
    }
}

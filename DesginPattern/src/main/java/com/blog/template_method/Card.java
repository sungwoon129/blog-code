package com.blog.template_method;

public class Card extends Payment{
    @Override
    void payByMethod() {
        System.out.println("카드 결제");
    }
}

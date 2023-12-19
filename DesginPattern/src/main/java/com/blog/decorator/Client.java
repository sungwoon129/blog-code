package com.blog.decorator;

public class Client {

    public void test() {
        Component detail = new ConcreteComponent();
        System.out.println("디테일 : " + detail.add());

        Component prefixStr = new PrefixDecorator(new ConcreteComponent());
        System.out.println("prefix : " + prefixStr.add());

        Component suffixStr = new SuffixDecorator(new PrefixDecorator(new ConcreteComponent()));
        System.out.println("suffix : " + suffixStr.add());
    }
}

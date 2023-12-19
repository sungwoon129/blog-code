package com.blog.flyweight;

import static com.blog.flyweight.FlyweightFactory.getCircle;

public class Client {

    public void test() {
        Shape circle = getCircle("red");
        Shape circle2 = getCircle("red");

        if(circle == circle2) {
            System.out.println("빨간 동그라미 객체는 하나만 생성되어있는 상태입니다.");
        }

    }

}

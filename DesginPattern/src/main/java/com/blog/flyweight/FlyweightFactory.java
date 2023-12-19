package com.blog.flyweight;

import java.util.HashMap;

public class FlyweightFactory {
    private static final HashMap<String, Circle> circleMap = new HashMap<>();

    public static Shape getCircle(String color) {
        // 기존 객체 재사용
        return circleMap.computeIfAbsent(color, Circle::new);
    }
}

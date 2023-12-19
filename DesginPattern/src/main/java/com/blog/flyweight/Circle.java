package com.blog.flyweight;

public class Circle implements Shape{
    private String color;
    private int x;
    private int y;
    private int radius;

    public Circle(String color) {
        this.color = color;
    }
    @Override
    public void draw() {
        System.out.println("Circle [color=" + color + ", x=" + x + ", y=" + y + ", radius=" + radius + "]");
    }
}

package com.blog.strategy;

public class Sword implements Weapon {
    @Override
    public void attack() {
        System.out.println("칼 휘두르기");
    }
}

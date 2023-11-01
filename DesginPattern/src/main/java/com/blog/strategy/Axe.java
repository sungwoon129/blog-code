package com.blog.strategy;

public class Axe implements Weapon {
    @Override
    public void attack() {
        System.out.println("도끼 내려찍기");
    }
}

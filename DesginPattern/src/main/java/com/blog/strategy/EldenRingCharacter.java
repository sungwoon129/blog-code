package com.blog.strategy;

public class EldenRingCharacter {

    private Weapon weapon;


    public void equipWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public void attack() {
        if(this.weapon == null) {
            System.out.println("주먹 내지르기");
        }
        else {
            weapon.attack();
        }
    }

}

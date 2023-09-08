package io.weyoui;

import java.util.Random;

public class User {
    private String id;
    private String name;
    private boolean isAuthenticated;


    public User() {
        Random random = new Random();
        this.id = String.valueOf(random.nextInt(100000));
    }

    public void login() {
        this.isAuthenticated = true;
    }

    public boolean hasPrinciple() {
        return isAuthenticated;
    }

}

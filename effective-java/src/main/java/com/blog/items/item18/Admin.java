package com.blog.items.item18;

import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements AdminInterface<User>  {
    private List<User> users = new ArrayList<>();

    public Admin(String username, String email, int age) {
        super(username, email, age);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void removeUser(User user) {
        users.remove(user);
    }

    @Override
    public User getUser(int idx) {
        return users.get(idx);
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public Admin getAdmin() {
        return this;
    }
}

package com.blog.items.item18;

import java.util.List;

public class ForwardingAdmin<E> implements AdminInterface<E> {
    private final AdminInterface<E> admin;

    public ForwardingAdmin(AdminInterface<E> admin) {
        this.admin = admin;
    }

    @Override
    public void addUser(User user) {
        admin.addUser(user);

    }

    @Override
    public void removeUser(User user) {
        admin.removeUser(user);
    }

    @Override
    public User getUser(int idx) {
        return admin.getUser(idx);
    }

    @Override
    public List<E> getAll() {
        return admin.getAll();
    }

    @Override
    public E getAdmin() {
        return admin.getAdmin();
    }
}

package com.blog.items.item18;

import java.util.List;
import java.util.Objects;

public class ForwardingAdmin<E> implements AdminInterface<E> {
    private final AdminInterface<E> admin;

    public ForwardingAdmin(AdminInterface<E> admin) {
        this.admin = admin;
    }

    public void addUser(User user) {
        admin.addUser(user);

    }


    public void removeUser(User user) {
        admin.removeUser(user);
    }


    public User getUser(int idx) {
        return admin.getUser(idx);
    }


    public List<E> getAll() {
        return admin.getAll();
    }


    public E getAdmin() {
        return admin.getAdmin();
    }

    @Override
    public boolean equals(Object o) {
        return admin.equals(o);
    }

    @Override
    public int hashCode() {
        return admin.hashCode();
    }

    @Override
    public String toString() {
        return admin.toString();
    }
}

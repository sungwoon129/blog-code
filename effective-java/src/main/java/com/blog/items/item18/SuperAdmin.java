package com.blog.items.item18;

import java.util.List;

public class SuperAdmin<E> extends ForwardingAdmin<E> {

    public SuperAdmin(AdminInterface admin) {
        super(admin);
    }

    @Override
    public List<E> getAll() {
        List<E> users = super.getAll();
        users.add(super.getAdmin());
        return users;
    }
}

package com.blog.items.item18;

import java.util.List;

public interface AdminInterface<E> {
    void addUser(User user);
    void removeUser(User user);
    User getUser(int idx);
    List<E> getAll();
    E getAdmin();
}

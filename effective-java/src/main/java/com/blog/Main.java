package com.blog;

import com.blog.items.item18.Admin;
import com.blog.items.item18.ForwardingAdmin;
import com.blog.items.item18.SuperAdmin;
import com.blog.items.item18.User;

public class Main {
    public static void main(String[] args) {
        Admin defaultAdmin = new Admin("관리자","관리자이메일",30);
        ForwardingAdmin<User> superAdmin = new SuperAdmin<>(defaultAdmin);

        System.out.println(defaultAdmin.getAll());
        System.out.println(superAdmin.getAll());

    }
}
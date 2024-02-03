package com.blog;

import com.blog.items.Item52.Client;
import com.blog.items.item18.Admin;
import com.blog.items.item18.ForwardingAdmin;
import com.blog.items.item18.SuperAdmin;
import com.blog.items.item18.User;

import java.util.List;

import static com.blog.items.item32.GenericAndVarArgs.expectToException;
import static com.blog.items.item32.GenericAndVarArgs.pickTwo;

public class Main {
    public static void main(String[] args) {

        /*Admin defaultAdmin = new Admin("관리자","관리자이메일",30);
        ForwardingAdmin<User> superAdmin = new SuperAdmin<>(defaultAdmin);

        System.out.println(defaultAdmin.getAll());
        System.out.println(superAdmin.getAll());

        // Item32 제네릭과 가변인수를 함께 쓸 때는 신중하라.
        expectToException();
        List<String> attributes = pickTwo("좋은", "빠른", "저렴한");*/

        // Item 52 다중정의는 신중히 사용해라
        Client client = new Client();
        client.overLoading();
        client.overRiding();
        client.setList();


    }
}
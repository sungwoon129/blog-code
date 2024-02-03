package com.blog.items.Item52;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class SetList {

    public void removeElement() {
        Set<Integer> set = new TreeSet<>();
        List<Integer> list = new ArrayList<>();

        for(int i = -3; i<3; i++) {
            set.add(i);
            list.add(i);
        }

        for(int i=0; i<3; i++) {
            set.remove(i);
            // 다중정의 메소드를 의도한대로 동작하게 만드는 방법
            list.remove(Integer.valueOf(i));
        }
        System.out.println(set + " " + list);
    }
}

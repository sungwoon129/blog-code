package com.blog.items.item32;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenericAndVarArgs {

    private static void dangerous(List<String>... stringLists) {
        List<Integer> intList = List.of(42);
        Object[] objects = stringLists;
        objects[0] = intList;
        String s = stringLists[0].get(0);
    }

    @SafeVarargs
    private static <T> List<T> flatten(List<? extends T>... lists) {
        List<T> result = new ArrayList<>();
        for(List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    private static <T> List<T> flattenV2(List<List<? extends T>> lists) {
        List<T> result = new ArrayList<>();
        for(List<? extends T> list : lists) {
            result.addAll(list);
        }
        return result;
    }

    public static <T> List<T> pickTwo(T a, T b, T c) {
        switch (ThreadLocalRandom.current().nextInt(3)) {
            case 0:
                return List.of(a, b);
            case 1:
                return List.of(a, c);
            case 2:
                return List.of(b, c);
        }
            throw new AssertionError();
    }

    public static void expectToException() {
        dangerous(List.of("a","b","c","d","e"));
    }




}

package com.blog.algorithm.sort;

import java.util.function.Function;

public interface SortAlgorithm {
    int[] sort(int[] arr);

    default void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    default void print(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
    }

}

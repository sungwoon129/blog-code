package com.blog.algorithm.sort;

public class BubbleSort implements SortAlgorithm {
    @Override
    public int[] sort(int[] arr) {

        long start = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i; j++) {

                if (j == arr.length - 1) break;


                if (arr[j] > arr[j + 1]) {
                    swap(arr, j, j + 1);
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("▼ 정렬 후");
        print(arr);

        System.out.println("수행시간 : " + (end - start));

        return arr;
    }
}

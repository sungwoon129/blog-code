package com.blog.algorithm;

import com.blog.algorithm.sort.BubbleSort;
import com.blog.algorithm.sort.SortAlgorithm;

import java.util.Random;

public class Main {
    public static void main(String[] args) {

        SortAlgorithm bubbleSort = new BubbleSort();

        int[] sample = intArrGenerator();

        System.out.println("▼ 정렬 전");
        bubbleSort.print(sample);
        bubbleSort.sort(sample);

    }

    public static int[] intArrGenerator() {

        int size = (int)(Math.random() * 10);
        int[] arr = new int[size];
        for(int i = 0; i< arr.length; i++) {
            int element = (int)(Math.random() * 100);
            arr[i] = element;
        }
        return arr;
    }


}
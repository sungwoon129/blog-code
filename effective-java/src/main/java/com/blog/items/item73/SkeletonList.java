package com.blog.items.item73;

import java.util.*;

public class SkeletonList {

    public List<Integer> intArrayList(int[] a) {
        Objects.requireNonNull(a);

        return new AbstractList<>() {
            @Override
            public Integer get(int i) {
                return a[i];
            }

            @Override
            public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;
                return oldVal;
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }

    public List<Integer> intSeqArrayList(int[] a) {
        Objects.requireNonNull(a);
        return new AbstractSequentialList<>() {

            @Override
            public Integer get(int index) {
                ListIterator<Integer> i = listIterator(index);

                try {
                    return i.next();
                } catch (NoSuchElementException e) {
                    throw new IndexOutOfBoundsException("인덱스 : " + index);
                }
            }

            @Override
            public ListIterator<Integer> listIterator(int index) {
                return this.listIterator(index);
            }

            @Override
            public int size() {
                return a.length;
            }
        };
    }
}

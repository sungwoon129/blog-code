package com.blog.items.item73;

import java.util.List;

public class Client {

    public void test() {
        SkeletonList skeletonList = new SkeletonList();
        List<Integer> test = skeletonList.intSeqArrayList(new int[]{1,2,3});

        test.get(5);
    }
}

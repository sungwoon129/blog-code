package com.blog;

import java.util.stream.Stream;

public class Sum {
    /**
     * 순차 리듀싱
     * 무한 스트림을 사용해 합계를 계산한다.
     */
    public long sequentialSum(long n) {
        return Stream.iterate(1L, i -> i + 1)
                .limit(n)
                .parallel()
                .reduce(0L, Long::sum);
    }

    /**
     * 반복형
     * 스트림을 사용하지 않고 전통적인 방식으로 합계를 계산해 반환한다.
     */
    public long iterativeSum(long n) {
        long result = 0;
        for (long i = 1L; i <= n; i++) {
            result += i;
        }

        return result;
    }
}

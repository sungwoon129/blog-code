package com.blog;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FunctionalProgrammingCalculator {

    public void calc() {
        // 1. 함수를 일급 객체로 다루기
        Function<Integer, Integer> square = x -> x * x;

        int result1 = square.apply(5);
        System.out.println("Square of 5: " + result1);

        // 2. 불변성을 유지하면서 상태 변경 피하기
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 기존의 방식: 각 요소를 제곱하여 새로운 리스트 생성
        List<Integer> squaredNumbersOld = numbers.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());

        // 함수형 프로그래밍 방식: 기존 리스트를 변경하지 않고 새로운 리스트 생성
        List<Integer> squaredNumbersNew = numbers.stream()
                .map(square)
                .collect(Collectors.toList());

        System.out.println("Original List: " + numbers);
        System.out.println("Squared Numbers (Old): " + squaredNumbersOld);
        System.out.println("Squared Numbers (New): " + squaredNumbersNew);
    }

}

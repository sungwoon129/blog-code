package com.blog;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectOrientedCalculator {
    int calc(Function<Integer, Integer> function, int value) {
        return function.apply(value);
    }

    List<Integer> applyToList(Function<Integer, Integer> function, List<Integer> values) {
        return values.stream()
                .map(function) // function 인터페이스는 함수형인터페이스이므로, 추상메소드인 apply를 map 함수에 전달한다.
                .collect(Collectors.toList());
    }

    public void calc() {

        // 1. 함수를 일급 객체로 다루기
        Function<Integer, Integer> square = (a) -> a * a;

        int result = this.calc(square, 5);
        System.out.println("Square of 5: " + result);

        // 2. 불변성을 유지하면서 상태 변경 피하기
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // 기존의 방식: 각 요소를 제곱하여 새로운 리스트 생성
        List<Integer> squaredNumbersOld = numbers.stream()
                .map(x -> x * x)
                .collect(Collectors.toList());

        // 객체지향 프로그래밍 방식: 기존 리스트를 변경하지 않고 새로운 리스트 생성
        List<Integer> squaredNumbersNew = this.applyToList(square, numbers);

        System.out.println("Original List: " + numbers);
        System.out.println("Squared Numbers (Old): " + squaredNumbersOld);
        System.out.println("Squared Numbers (New): " + squaredNumbersNew);
    }
}

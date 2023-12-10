package com.blog;


@FunctionalInterface
public interface FirstClassCitizen<T> {
    // 하나의 추상 메소드만 허용. 함수형 인터페이스에서 하나의 추상메소드만을 허용하는 이유는 람다표현식을 명확하게 하기 위해서입니다.
    // FirstClassCitizen<String> firstClassCitizen = () -> "abstract method"; 이런식으로 사용하려면, 하나의 추상메소드만을 허용해야 하기 때문입니다.
    // 함수형 인터페이스는 하나의 기능을 표현하는 데 사용됩니다.
    T getAbstractStr();

    // default method
    default void printDefault() {
        System.out.println("call default method");
    }

    // static method
    static void printStatic() {
        System.out.println("call Static method.");
    }
}

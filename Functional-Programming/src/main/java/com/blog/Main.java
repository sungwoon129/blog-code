package com.blog;

import java.util.function.Function;

public class Main {
    public static void main(String[] args) {

        // 추상 메소드 구현
        FirstClassCitizen<String> firstClassCitizen = () -> "abstract method";

        // abstract method
        String str = firstClassCitizen.getAbstractStr();
        System.out.println(str);

        // default method
        firstClassCitizen.printDefault();

        // static method
        FirstClassCitizen.printStatic();

        // 일급 객체
        FirstClassCitizen<String> firstCondition = () -> "@FunctionalInterface는 변수에 할당 가능";
        GeneralInterface<String> generalInterface = () -> "@FunctionalInterface는 변수에 할당 가능";


    }

    public String secondCondition(FirstClassCitizen<String> arg) {
        return arg.getAbstractStr();
    }
    public FirstClassCitizen<String> ThirdCondition() {
        return () -> "함수의 반환값으로 사용할 수 있는 객체(일급 객체의 조건)";
    }
    public GeneralInterface<String> qq() {
        return () -> "함수의 반환값으로 사용할 수 있는 객체(일급 객체의 조건)";
    }

}
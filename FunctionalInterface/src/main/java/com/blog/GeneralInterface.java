package com.blog;

public interface GeneralInterface<T> {

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

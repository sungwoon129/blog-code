package com.blog.proxy;

public class RealImage implements Image {
    private final String filename;

    public RealImage(String filename) {
        this.filename = filename;
        load();
    }

    private void load() {
        System.out.println("이미지 로드");
    }
    @Override
    public void display() {
        System.out.println("이미지 디스플에이");

    }
}

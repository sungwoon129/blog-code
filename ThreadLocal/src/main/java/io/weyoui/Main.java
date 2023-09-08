package io.weyoui;

public class Main {
    public static void main(String[] args) {

        UserService userService = UserService.getInstance();

        Thread thread1 = new Thread(userService);
        Thread thread2= new Thread(userService);

        thread1.setName("thread1");
        thread2.setName("thread2");

        thread1.start();
        thread2.start();

    }
}
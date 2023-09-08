package io.weyoui;

public class UserService implements Runnable {

    private static UserService instance;
    private boolean isAuthenticated = false;

    private UserService() {}

    public static synchronized UserService getInstance() {
        if(instance == null) {
            instance = new UserService();
        }
        return instance;
    }

    @Override
    public void run() {

        Thread thread = Thread.currentThread();

        if(thread.getName().equals("thread1")) {
            isAuthenticated = true;
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if(isAuthenticated) {
            System.out.println(thread.getName() + " : authenticated user!");
        }
    }
}

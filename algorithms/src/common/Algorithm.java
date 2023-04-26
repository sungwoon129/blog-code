package common;

public interface Algorithm {
    default void run() {
        implLogic();
        print();
    }

    void implLogic();

    void print();
}

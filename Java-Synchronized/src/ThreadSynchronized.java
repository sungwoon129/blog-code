

public class ThreadSynchronized {
    public static void main(String[] args) {

        Task task = new Task();
        Thread thread1 = new Thread(task);
        Thread thread2 = new Thread(task);

        thread1.setName("1번 스레드");
        thread2.setName("2번 스레드");

        thread1.start();
        thread2.start();
    }
}

class Task implements Runnable {

    Account acc = new Account();

    @Override
    public void run() {
        while (acc.balance > 0 ) {
            int money = (int) (Math.random() * 3 + 1) * 100;
            acc.withdraw(money);
        }

    }
}

class Account {

    int balance;

    public Account() {
        this.balance = 1000;
    }

    public synchronized void withdraw(int money) {

        if(balance >= money) {
            try {
                Thread thread = Thread.currentThread();
                System.out.println(thread.getName() + " 출금금액 = " + money);
                Thread.sleep(1000);
                balance -= money;
                System.out.println(thread.getName() + "balance =" + balance);
            } catch (Exception e) {

            }
        }
    }
}
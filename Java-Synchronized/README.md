# Java Synchronized 활용 #

<hr />

### 1.Thread-safe ###
우리가 컴퓨터에서 하나의 프로세스(프로그램)을 실행하면, 운영체제는 프로그램에 메모리를 할당하여 프로그램이 정상적으로 동작하게  
해줍니다. 프로세스는 프로그램에서 사용하는 데이터와 메모리와 같은 자원 그리고 스레드(thread)로 구성됩니다.
<br />  
스레드는 프로세스내에서 실제로 작업을 수행하는 주체를 의미하는데, 프로세스는 반드시 하나이상의 스레드를 가집니다.  
하나이상이라고 했으니 2개이상의 스레드가 하나의 프로세스 안에 존재할 때 멀티스레드 프로세스라고 합니다.  
<br />
멀티스레드프로세스 환경에서 각 스레드들은 작업들을 수행합니다. 이 과정에서 동일한 자원을 스레드가 필요로 하는 경우가 있습니다.  
자바코드상에서는 <u> 비슷한 시점에 동일한 객체를 호출한다고 표현할 수 있습니다. </u> 이 경우에 개발자가 의도하지 않은 현상이 발생할 수 있습니다.  
이렇게 스레드간에 비슷한 시점에 동일한 자원을 필요로 하고 두 개 이상의 스레드가 하나의 자원에 동시에 접근이 가능할 때, thread-safe 하지 않다고 표현합니다.  

### 2.Thread-safe 하지 않은 상황 ### 
은행 계좌에서 돈을 인출하는 상황을 생각해보면, 계좌에 들어있는 잔액이상을 인출할 수 없어야 합니다. 
하나의 스레드에서 구현해보면 다음과 같은 코드를 생각할 수 있습니다.
``` java
public class ThreadSynchronized {
    public static void main(String[] args) {

        Task task = new Task();
        Thread thread1 = new Thread(task);

        thread1.setName("1번 스레드");

        thread1.start();
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

    public void withdraw(int money) {

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
```

1번 스레드에서 잔액이 0원 초과인 경우 100 or 200 or 300원을 차감을 반복하는 코드입니다.  
실행하면 다음과 같은 결과가 나타납니다.
<br />

![image](https://user-images.githubusercontent.com/43958570/235356630-2266b9a3-5823-40a0-b519-5faa6ebe9600.png)

하나의 스레드에서는 의도한대로 0원일 때는 차감되지 않습니다.
그렇다면 멀티스레드 환경에서는 어떻게 동작할지 확인해 보겠습니다.

``` java
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

    public void withdraw(int money) {

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
```
스레드를 하나 더 늘려주고, 두 스레드가 모두 Account 클래스라는 하나의 자원을 필요로 합니다.  
하나의 스레드가 Account 클래스를 사용중일 때(Account 내부코드에 접근할 때) 다른 하나의 클래스는 어떻게 동작할까요?
실행하여 결과를 확인해보겠습니다.

![image](https://user-images.githubusercontent.com/43958570/235359114-3c0df9b1-1ce7-4496-b473-affc8fbcfa94.png)
<br />

코드가 의도하는 방향은 0원에서 금액이 차감이 되지 않아야 하지만 2번스레드가 0원에서 300원을 차감하여 잔액이 -300원이  
된 것을 볼 수 있습니다.    
이렇게 된 이유는 하나의 스레드가 Account 코드에 진입하여 내부를 실행하는 도중에 다른 스레드 또한  
Account 클래스의 내부코드에 접근이 가능하기 때문입니다.  
실행결과에서 출력결과를 살펴보면, 중간중간 1번스레드 혹은 2번스레드의 작업이 끝나지않아 출금 후 잔액이 나타나지 않았는데도 불구하고,  
다른 스레드에서 잔액을 차감하는 것을 확인할 수 있습니다. 그래서 하나의 스레드에서 잔액을 차감하는 코드가 실행되지 않은 상태에서, 다른 스레드가 잔액을  
확인했을 때 0을 초과하는 값으로 인식해 또 차감하다 보니 위와 같은 결과가 나온것입니다.  
어떻게 하면 해결할 수 있을까요?  
balance 데이터에 두 개의 스레드가 동시에 접근해서 문제가 발생했으니까 balance 데이터에 동시에 접근하지 못하도록 하는 방법을 생각해 볼 수 있습니다.
그렇다면 코드에서 balance 데이터에 대해서 스레드간에 동기화가 필요합니다.
여기서 '스레드간의 동기화'라는 표현은 두 개 이상의 스레드가 하나의 자원에 동시에 접근하지 못하도록 하는 것을 의미합니다.
이것을 thread-safe 하다고 표현하기도 합니다.
<br />

### 3.synchronized를 활용하는 이유와 활용방법 ###

이 때 필요한 키워드가 <b> Synchronized </b> 입니다.  
synchronized는 하나의 스레드가 클래스 내부를 지나고 있는 동안 다른 스레드의 접근을 막습니다.
synchronized 키워드를 활용해 보겠습니다.
``` java
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
```
withdraw 메소드의 리턴타입 앞에 해당 키워드를 선언해주었습니다.
메소드 레벨에 선언된 synchronized는 메소드를 포함하고 있는 인스턴스에 대한 다른 스레드의 접근을 막습니다.
여기서는 Task 클래스에서 선언한 Account 클래스를 의미합니다.

synchronized는 다음과 같이 메소드 레벨에서 선언하지 않고 메소드 내부에 블록 형태로도 사용하여 범위를 제한할 수 있습니다.
```java
public static Singleton getInstance(){
    if(instance == null){
        synchronized (Singleton.class) {
            if(instance == null){
                instance = new Singleton();
            }
        }
    }
    return instance;
}
```
이 코드는 싱글톤 패턴을 구현할 때 멀티스레드 환경에서 thread-safe한 싱글톤 객체를 생성할 때, 사용되는 방법입니다.
synchronized 키워드 옆의 소괄호 안에는 동기화 할(접근을 막을) 클래스를 명시합니다.

다시 계좌 인출코드로 돌아와서 synchronized를 선언했을 때 어떻게 동작하는지 확인해보겠습니다.

![image](https://user-images.githubusercontent.com/43958570/235361025-f117d8fa-3a2d-47fb-bd25-e9a422b62b41.png)

이제는 thread-safe 하기 때문에 잔액이 0원일 때 출금하지 않고 의도한대로 동작하는 모습을 볼 수 있습니다.
혹시 운이 좋아서 출금금액이 정확히 맞아 떨어지거나, 두 스레드의 동작이 우연히 겹치지않아서 0원이 되었을 수도 있으므로 여러번  
반복해서 실행해도 아까처럼 잔액이 -가 되지 않는 것을 확인할 수 있습니다.  

### 4.정리 및 주의사항 ###

이렇게 <b> synchronized </b>키워드를 활용해 thread-safe한 코드를 작성하는 방법을 알아보았습니다.
synchronized는 스레드간의 간섭을 막아주는 용도로 유용하게 활용할 수 있지만, 다른 스레드의 접근을 제어하기 때문에 성능을 크게
떨어뜨릴 수 있습니다. 꼭 필요한 경우에만 사용하고, 가능하다면 블록을 활용하여 적용되는 범위를 제한하는 것이 좋습니다.

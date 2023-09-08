## Thread 별로 가지는 저장 공간 ThreadLocal ##

[Blog 링크](https://style-tech.tistory.com/34)

![image](https://github.com/sungwoon129/blog-code/assets/43958570/3176af6d-77d8-4d07-926b-245a3f191b14)

### **ThreadLocal 이란?**

**ThreadLocal**은 쓰레드 별로 가지는 각각의 저장공간을 제공하는 컨테이너입니다.

멀티 스레드환경에서 쓰레드별로 고유한 저장공간을 제공하여, 하나의 서비스를 여러 쓰레드가 공유하는 경우에 활용할 수 있습니다.

예를들어, 사용자 인증이 완료되면 서버의 자원을 반환하는 기능이 있다고 합시다.

멀티쓰레드 환경에서 이 기능은 하나의 쓰레드에서 인증이 완료되었다고 해도, 다른 쓰레드에서 인증이 완료되지 않았다면 다음 단계로 넘어가서는 안됩니다.

하지만 Spring 프레임워크를 통해 만든 서비스라면, Spring Bean은 싱글톤 패턴을 이용해 생성되기 때문에 하나의 객체를 공유합니다. 아마 따로 처리를 하지 않는다면 인증이 완료되지 않은 쓰레드에서도 서버의 자원에 접근이 가능할 것입니다.

예시를 통해 알아보도록 하겠습니다.

### **ThreadLocal을 사용하지 않는 경우**

```
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
```

```
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
```

위 코드는 2개의 쓰레드에서 싱글톤 클래스인 UserService를 통해 특정 쓰레드에서만 인증처리를 하고 인증된 경우 정해진 메시지를 출력하는 코드입니다.

현재 쓰레드가 thread1인 경우에만 인증처리를 하고 메시지를 출력합니다.

main 메소드를 실행해서 어떤 결과가 나오는 지 확인해보겠습니다.

![image](https://github.com/sungwoon129/blog-code/assets/43958570/faee4fff-6094-4541-8d1d-d6f136890dda)

인증은 thread1이 했지만, thread2도 메시지를 출력합니다.

UserService는 모든 쓰레드에서 공유하는 싱글톤 객체이기 때문에, 모든 데이터를 쓰레들 끼리 공유합니다.

인증정보 뿐만 아니라, 암호를 변경한다거나, 고유한 개인정보를 공유하는 일이 발생한다면 매우 위험할 것입니다.

이런 이유로 서비스를 쓰레드들이 공유하더라도 쓰레드별로 고유한 정보를 저장하는 공간인 **ThreadLocal**이 필요한 것입니다.

### **ThreadLocal 동작원리**

ThreadLocal 어떤 방식으로 데이터를 관리하여 쓰레드별로 고유한 저장공간을 가질 수 있는지 알아보도록 하겠습니다.

자바에서 제공하는 Thread 클래스의 내부 코드를 살펴보면 Thread는 ThreadLocal.ThreadLocalMap 이라는 인스턴스를 가지고 있습니다.

```
public class Thread implements Runnable {
	// ...

	/* ThreadLocal values pertaining to this thread. This map is maintained
     * by the ThreadLocal class. */
    ThreadLocal.ThreadLocalMap threadLocals = null;
    
    // ...
}
```

그리고 다음은 ThreadLocal의 코드 중 일부를 가져왔습니다.

```
public class ThreadLocal<T> {

    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            map.set(this, value);
        } else {
            createMap(t, value);
        }
        if (this instanceof TerminatingThreadLocal) {
            TerminatingThreadLocal.register((TerminatingThreadLocal<?>) this);
        }
        return value;
    }

	public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
    
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            map.set(this, value);
        } else {
            createMap(t, value);
        }
    }
    
    void createMap(Thread t, T firstValue) {
        t.threadLocals = new ThreadLocalMap(this, firstValue);
    }
    
    static class ThreadLocalMap {
            static class Entry extends WeakReference<ThreadLocal<?>> {
            
            Object value;

            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
}
```

위코드를 보면 Thread는 ThreadLocal.ThreadLocalMap 이라는 인스턴스를 가지고 있고, ThreadLocalMap은 ThreadLocal 클래스의 내부 클래스로 ThreadLocal의 get,set 메소드를 통해서 접근할 수 있습니다.

ThreadLocalMap은 key,value로 구성된 Map 형태의 자료구조를 가지고 있고,

ThreadLocal이 Thread 별로 고유한 공간을 가지는 이유는 ThreadLocal의 get, set 메소드의 내부를 보면 Thread.currentThread() 메소드를 통해 현재 쓰레드의 ThreadLocalMap에 접근하기 때문입니다.

![image](https://github.com/sungwoon129/blog-code/assets/43958570/a63cd88e-0f65-4692-9265-02eaa9193462)

### **synchronized 와 비교하면?**

위에서 ThreadLocal이 필요한 이유로 여러 쓰레드가 하나의 자원을 공유하기 때문이라고 하였습니다.

이렇게 멀티쓰레드 환경에서 쓰레드들 간의 자원공유, 경합등으로 발생하는 문제를 동시성 문제라고 합니다.

그리고 서비스에서 동시성 문제가 발생하지 않는 상태를 Thread-Safe 하다고 표현합니다.

서비스가 Thread-Safe 상태를 구현하는 방법에는 ThreadLocal만 있는 것은 아닙니다.

메소드나 인스턴스 변수에 synchronized 키워드를 선언해서 동시성 문제를 해결할 수도 있습니다.

하지만 ThreadLocal과 synchronized는 사용하는 방식에 차이가 있습니다.

-   **synchronized : 쓰레드별로 같은 변수에 접근해서 사용하는 경우, 자원 경합이 발생해 하나의 쓰레드가 사용중인 자원에 다른 쓰레드가 접근하지 못하도록 하는 키워드**
-   **ThreadLocal : 쓰레드별로 다른 저장 공간을 만들어, 동일한 서비스에서 다른 변수를 사용하고 싶은 경우에 활용**

이렇게 synchronized와 ThredLocal은 쓰레드별로 다른 자원을 사용하냐, 같은 자원을 사용하냐에 따라 활용하는 경우가 완전히 다릅니다.

구현하는 서비스에 따라 적절한 방법을 선택하는 것이 좋습니다.

### **ThreadLocal의 사용처**

위에서 ThreadLocal을 사용하지 않는 경우에 대한 예시코드에서 사용자 인증문제를 예시로 들어 이야기했습니다.

예시처럼 ThreadLocal은 쓰레드별로 다른 상태를 가져야하는 경우에 활용하기 좋기 때문에 인증 정보, 트랜잭션 상태 전이에 많이 활용됩니다.

Spring에서도 인증.권한.인가 프레임워크로 자주 사용되는 Spring Security 에서 ThreadLocal이 사용됩니다.

Spring Security는 Security Context 안에서 Authentication이라는 클래스 객체로 인증정보를 보관합니다.

SecurityContext는 SecurityContextHolder 내부에 존재하고 있습니다.  

![image](https://github.com/sungwoon129/blog-code/assets/43958570/3412d405-c6be-4b72-a3e1-6e11e1eb4859)

SecurityContextHolder는 SecurityContext를 저장하는 방식을 여러가지 가지고 있고, 기본으로 사용하는 방식이 MODE\_THREADLOCAL로 ThreadLocal을 사용하여 동일한 쓰레드 내에서 인증정보를 공유합니다.

```
public class SecurityContextHolder {
	public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";

	public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";

	public static final String MODE_GLOBAL = "MODE_GLOBAL";
    
    	private static void initializeStrategy() {
		if (MODE_PRE_INITIALIZED.equals(strategyName)) {
			Assert.state(strategy != null, "When using " + MODE_PRE_INITIALIZED
					+ ", setContextHolderStrategy must be called with the fully constructed strategy");
			return;
		}
		if (!StringUtils.hasText(strategyName)) {
			// Set default
			strategyName = MODE_THREADLOCAL;
		}
		if (strategyName.equals(MODE_THREADLOCAL)) {
			strategy = new ThreadLocalSecurityContextHolderStrategy();
			return;
		}
		if (strategyName.equals(MODE_INHERITABLETHREADLOCAL)) {
			strategy = new InheritableThreadLocalSecurityContextHolderStrategy();
			return;
		}
		if (strategyName.equals(MODE_GLOBAL)) {
			strategy = new GlobalSecurityContextHolderStrategy();
			return;
		}
		// Try to load a custom strategy
		try {
			Class<?> clazz = Class.forName(strategyName);
			Constructor<?> customStrategy = clazz.getConstructor();
			strategy = (SecurityContextHolderStrategy) customStrategy.newInstance();
		}
		catch (Exception ex) {
			ReflectionUtils.handleReflectionException(ex);
		}
	}
    
    // ...

}
```

```
final class ThreadLocalSecurityContextHolderStrategy implements SecurityContextHolderStrategy {
	private static final ThreadLocal<Supplier<SecurityContext>> contextHolder = new ThreadLocal<>();
  
	@Override
	public void setContext(SecurityContext context) {
		Assert.notNull(context, "Only non-null SecurityContext instances are permitted");
		contextHolder.set(() -> context);
	}
}
```

### **ThreadLocal 사용 시 주의사항**

ThreadLocal은 서로 다른 쓰레드끼리는 다른 상태를 가질 수 있지만, 동일한 쓰레드 내에서는 동일한 데이터를 가지고 있습니다. 일반적으로 웹 애플리케이션을 개발할 때 사용되는 WAS(Web Application Service)에서는 Thread를 생성하는 비용이 매우 비싸기 때문에, WAS가 실행될 때 Thread Pool이라는 공간에 Thread들을 미리 생성(Spring의 경우 200개)해서 관리합니다. 그리고 사용자의 요청이 있을 때, 여유가 있는 쓰레드를 할당해서 요청을 처리하고 반환받는 형태로 쓰레드를 관리합니다.

요청을 처리한 Thread를 Thread Pool에서 제거하고 새로 생성하는 방식으로 관리하지 않기 때문에, 이전 요청을 처리하면서 ThraedLocal에 데이터가 남아있다면, 다른 사용자가 요청을 했을 때 데이터에 접근할 수 있을 뿐만 아니라 위.변조도 가능합니다. 

그래서 요청을 처리하고 난 후 **Thread를 ThreadPool에 다시 반환하기 전에 ThreadLocal을 초기화** 시켜줘야 합니다.

ThreadLocal의 remove() 메소드를 통해 ThreadLocal 내부의 ThreadLocalMap의 데이터를 제거할 수 있습니다.

'ThreadLocal 사용처'에서 이야기한 Spring Security 같은 경우에는 clearContext라는 메소드를 통해서 정보를 초기화하는데,  기본 전략인 ThreadLocalSecurityContextHolderStrategy에서 구현한 clearContext 메소드 또한 ThreadLocal의 remove 메소드를 통해서 Thread를 반환하기전에 데이터를 초기화시켜줍니다.
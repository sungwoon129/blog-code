## Spring Event를 활용한 Slack 메시지 전송 ##

### 프로젝트 소개 ###

Spring Event 기능을 활용해서 주문 시스템에서 주문이 발생하면, Slack에 '주문알람' 메시지가 전송되는 기능을 구현한 프로젝트입니다.
REST API 만으로 구현했을 때 도메인계층에서 로직이 섞이는 부분이 있어서 Spring Event를 활용하면 로직이 도메인 계층에서 섞이지 않고, 주문했을 
때 문자메시지를 발송하는 기능과 같은 추가기능을 필요로 할 때도 확장이 용이할 것 같다는 생각에 구현해보았습니다.

<br> 

### 요구사항 ###
+ 상품 기능
    + 상품 등록
+ 주문 기능
    + 상품 주문

<br>

### UML ###

![Spring Event EXAMPLE UML drawio](https://github.com/sungwoon129/blog-code/assets/43958570/21180f8a-1311-4700-b8af-96ed600f5c43)


<br>

### Spring Event를 사용한 이유 ###
어플리케이션을 개발하고 운영하다보면 다양한 추가기능이 생기고 수정사항이 생깁니다. 주문 기능을 생각해봅시다.
처음에는 정말 주문기능 하나만 가지고 개발이 되었더라도 서비스가 발전하면서 다양한 요구가 발생합니다. 주문이 발생하면 
구매자에게 메시지를 보낸다거나 메일을 발송하는등의 여러가지 기능이 필요하게 됩니다. Order 애그리거트에 주문이 발생하면 
알람을 발송하는 코드를 작성하면 아마 다음과 같을 것입니다.

```java
public class Order {
  ...
  // Order 애그리거트 외부의 기능을 실행하기 위해 다른 도메인 서비스를 파라미터로 전달받음
  public Order(...,AlarmService alarmService) { // Order 생성자 파라미터들
    ... 
      try {
        alarmService.send(orderer,shippingInfo); // 메시지 발송에 필요한 정보
      } catch (Exception e) {
        ...
    }
  }
}
```
응용서비스 계층에서 처리할 수도 있습니다.

```java
public class placeOrderService {
    private AlarmService alarmService;
    ...
  
  @Transactional
  public OrderNo placeOrder(OrderRequest orderRequest) {
    ... 
    orderRepository.save(order); // 주문 처리
    alarmService.send(order.orderer,order.shippingInfo);
  }
  
}
```
많은 경우 알람(문자,카카오톡메시지,이메일등) 발송서비스는 외부 서비스를 이용합니다.  
외부서비스를 이용하기 때문에 다음과 같은 문제들에 대한 고민이 생깁니다.

> 1. 알람 발송 서비스에서 예외가 발생한 경우의 트랜잭션처리
> 2. 알람 발송 서비스의 응답시간이 길어질 경우 주문기능 성능 이슈
> 3. Order 도메인내의 로직이 뒤섞이는 문제

1번은 주문기능은 정상적으로 처리되었지만 알람을 발송하는 과정에서 예외가 발생한 경우 트랜잭션이 모두 롤백이 됩니다.
하지만 반드시 롤백을 할 필요는 없습니다. 기능 요구사항에 따라 다르겠지만, 알람 발송의 경우 조금 늦어도 괜찮은 경우도 많습니다.
그래서 실패한 알람발송을 이후 재시도해서 처리하는 방법도 있습니다. 

2번은 주문기능의 성능이 외부서비스에 직접적으로 영향을 받는다는 것입니다. 외부서비스의 응답시간이 느려지게 되면 전체 주문기능의
응답시간도 느려지게 됩니다.

3번은 Order 도메인 객체는 주문과 관련된 기능만을 처리하지만, 알람 발송서비스의 로직이 섞인다는 것입니다.
주문 도메인에 주문 로직과 알람 로직 둘다 있습니다. 지금은 알람 서비스 하나 뿐이지만 점차 기능이 추가되면서 더 많은 다른 도메인의
로직이 추가될 수도 있습니다.  

위에서 이야기한 문제점들은 주문 도메인과 알림 도메인의 결합도가 너무 높아서 생기는 문제입니다.
그리고 Event는 도메인 사이의 결합도를 낮출 수 있는 좋은 방법 중 하나입니다.

<br>

### Spring Event 구현 ###

이벤트를 활용하는 방법에는 크게 두가지가 있습니다. 하나는 트리거로 활용하는 방법이고, 다른 하나는 데이터 동기화로 활용하는 방법입니다.
이번 프로젝트에서는 '주문이 발생하면 알림을 보낸다' 라는 기능을 구현할 것이므로 트리거로써 활용할 것입니다.

이벤트는 아래 그림과 같은 구조로 동작합니다.

<br>

![이벤트 구성요소](https://github.com/sungwoon129/blog-code/assets/43958570/b5eac786-2bbd-49af-8fc9-c8dbb5ddf0a1)

<br>
도메인 모델에서 이벤트 생성 주체는 엔티티, 밸류, 도메인 서비스와 같은 도메인 객체가 해당됩니다. 도메인 객체의 상태가 변경되면 이벤트를 발생시키는 역할을 합니다.

이벤트 핸들러는 이벤트가 발생하면 이벤트 객체를 전달받아 이벤트 객체를 활용해서 기능을 수행합니다. 여기서는 주문정보를 가지고 있는 이벤트 객체를 전달받아서 알림을 보내는 기능을 수행합니다.

이벤트 디스패처는 이벤트 생성 주체와 이벤트 핸들러 사이에서 이벤트 객체를 전달하는 역할을 합니다. 이벤트 생성 주체와 이벤트 핸들러는 직접적인 연관이 없습니다. 이벤트 생성 주체는 이벤트 디스패처에 이벤트를 전달하고 이벤트 핸들러는 이벤트 디스패처에게 이벤트를 전달받습니다. 디스패처는 사이에서 중간자  
역할을 담당합니다. 직접적인 연관을 없애고 간접적으로 연결하여 결합도를 낮출 수 있습니다.
이벤트 디스패처가 이벤트 핸들러에게 이벤트를 전달하는 행위를 전파(dispatch) 혹은 발행(publish)라고 합니다. 그리고 이벤트 핸들러는 이벤트 디스패처를 구독(subscribe)하고 있다고 표현합니다. 
알림 서비스 기능은 이벤트 핸들러에 구현되어 있습니다. 이벤트 핸들러는 이벤트 디스패처에게 이벤트를 전닯받아야 하므로, 이벤트 디스패처가 이벤트의 동작방식을 제어한다고 볼 수 있습니다. 그래서 이벤트 디스패처의 구현방식에 따라 이벤트 생성과 처리를 동기적으로 할지, 비동기적으로할지 선택할 수 있습니다.

<br>
이벤트 객체 다음과 같이 표현할 수 있습니다.

<br>

```java
@Getter
public class OrderPlacedEvent {

  private ShippingInfo shippingInfo;
  private List<OrderLine> orderLines;
  private LocalDateTime orderDate;


  protected OrderPlacedEvent() {}

  public OrderPlacedEvent(ShippingInfo shippingInfo, List<OrderLine> orderLines, LocalDateTime orderDate) {
    this.shippingInfo = shippingInfo;
    this.orderLines = orderLines;
    this.orderDate = orderDate;
  }
}
```
알림 메시지를 표현하기 위해 필요한 정보인 주문자 정보와 주문상품들, 주문날짜등에 대한 정보가 담겨있습니다.
이 이벤트를 발생시키는 주체는 Order 애그리거트가 될 것입니다.

<br>

```java
@Entity
public class Order {
  ...
  public Order(OrderNo number, Orderer orderer, ShippingInfo shippingInfo, List<OrderLine> orderLines ) {
    ...
    Events.raise(new OrderPlacedEvent(shippingInfo,orderLines,orderDate));
  }
  ...
}
```

여기서는 Events 클래스의 raise 메서드를 통해서 이벤트를 발생시킵니다. 
Events 클래스와 raise 메서드는 스프링에서 제공하는 ApplicationEventPublisher를 활용해서 만든 클래스와 메서드입니다.

<br>

```java
public class Events {
    private static ApplicationEventPublisher publisher;

    static void setPublisher(ApplicationEventPublisher publisher) {
        Events.publisher = publisher;
    }

    public static void raise(Object event) {
        if(publisher != null) {
            publisher.publishEvent(event);
        }
    }
}
```

Events 클래스는 setPublisher 메서드를 통해서 ApplicationEventPublisher를 전달받고 있습니다.
ApplicationEventPublisher를 전달해주기 위해서는 아래와 같은 스프링 설정클래스가 필요합니다.

<br>

```java
@Configuration
public class EventsConfiguration {

    @Autowired
    private ApplicationContext context;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Events.setPublisher(context);
    }
}
```

ApplicationContext는 애플리케이션에 대한 구성정보를 제공하는 인터페이스입니다. 스프링 컨테이너로서 Bean들의 정보를 포함하고 
있습니다. 그리고 ApplicationEventPublisher를 상속하고 있습니다. 스프링에서 제공하는 인터페이스이므로 이미 구현되어 있기때문에 별도로  
구현하지 않고 @Autowired로 애플리케이션 구성정보를 가져올 수 있습니다. 그리고 eventsInitializer 메서드의 반환타입인  
InitializingBean은 스프링 Bean 객체를 초기화할 때 사용하는 인터페이스로, Events#setPublisher() 메서드에 ApplicationContext를  
전달해서 Events 클래스를 초기화합니다.

이제 이벤트가 발생하면 알림을 전송하는 기능을 구현한 이벤트 핸들러가 필요합니다.

<br>

```java
@Service
public class OrderPlacedEventHandler {
    private AlarmService alarmService;

    public OrderPlacedEventHandler(AlarmService alarmService) {
        this.alarmService = alarmService;
    }


    @Async
    @EventListener(OrderPlacedEvent.class)
    public void handle(OrderPlacedEvent evt) {
        alarmService.sendAlarm(evt);

    }
}
```

ApplicationEventPublisher#publishEvent() 메서드를 실행할 때 OrderPlacedEvent 타입 객체를 전달하면  OrderPlacedEvent  
값을 갖는 @EventListener 어노테이션을 가진 메서드를 찾아서 실행합니다. @Async 어노테이션은 이 메서드가 비동기적으로 실행되게   
합니다. 이벤트 핸들러를 비동기적으로 실행하면, 주문기능이 외부서비스의 성능에 영향을 받는 문제를 해결할 수 있습니다. 

비동기적으로 실행되는 이벤트 핸들러는 주문 로직을 수행하는 스레드와 다른 스레드에서 수행되므로 알림 발송기능이 처리되는데 오랜 시간이
걸리더라도 주문기능의 성능에 영향을 주지 않습니다. @Async 어노테이션을 사용하기 위해서는 @EnableAsync 어노테이션을 사용해서
비동기 기능을 활성화 해주어야 합니다.

<br>

```java
@EnableAsync
@SpringBootApplication
public class SpringEventWithSlackApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEventWithSlackApplication.class, args);
	}

}
```

그 다음은 알림을 발송하는 AlarmService를 구현하는 일입니다. 구현한 코드까지 이미지를 첨부하는 것은 주제에 어긋나다고 생각해서
깃허브 저장소에 올려두었습니다. 

이제는 구현한 Spring Event가 정상적으로 동작하는지 확인하기 위해 테스트를 해보겠습니다.

<br>

```java
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OrderControllerTest {

    @LocalServerPort
    int port;

    @Autowired
    PlaceOrderService placeOrderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    MockMvc mvc;


    @BeforeEach
    public void setup() {
        ProductId productId = new ProductId("1");
        Product sampleProduct = new Product(productId,"New jeans - hype boy",new Money(10000));
        productRepository.save(sampleProduct);
    }
    
    @Test
    public void 상품을_등록한다() {
        ProductId productId = new ProductId("1");
        Product sampleProduct = new Product(productId,"New jeans - hype boy",new Money(10000));
        productRepository.save(sampleProduct);

        Optional<Product> product = productRepository.findById(productId);
        assertThat(product.isPresent() ? product.get().getId() : null).isEqualTo(sampleProduct.getId());
    }

    @Test
    void 주문을_넣고_슬랙알람이벤트가_발생한다() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        MemberId id = MemberId.of("tester");
        OrderProduct orderProduct = new OrderProduct("1",2);
        List<OrderProduct> orderProducts = List.of(orderProduct);
        Receiver receiver = new Receiver("swy","01012345678");

        ShippingInfo shippingInfo = new ShippingInfo(new Address("123-123","korea","home"),"배송합니다",receiver);

        OrderRequest orderRequest = new OrderRequest();
        orderRequest.setOrdererMemberId(id);
        orderRequest.setOrderProducts(orderProducts);
        orderRequest.setShippingInfo(shippingInfo);

        String url = "http://localhost:" + port + "/" + "orders/order";

        String data = objectMapper.writeValueAsString(orderRequest);
        
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(data)
        ).andExpect(status().isOk());
    }
}


```

![image](https://github.com/sungwoon129/blog-code/assets/43958570/0194fb79-58ab-473d-9262-5697dfbd72c5)

![image](https://github.com/sungwoon129/blog-code/assets/43958570/82123542-5c42-4b5c-bd0b-cc6fc5e7776d)

기능이 잘 동작하는 것을 확인할 수 있습니다.

### 마무리 ###

간단하게 Spring Event 기능을 활용한 알림 기능을 구현해 보았습니다. 하지만 이 코드에는 아직 해결해야할 문제들이 있습니다.
현재 코드에서는 알림 발송을 실패했을 때, 예외를 발생시키고 다른 처리는 하지 않습니다. 하지만 운영중인 실제 서비스에서는 발송내역을
관리를 해야합니다. 발송 실패한 알림들을 재전송한다거나 실패한 원인을 분석할 수 있도록 로그를 남기는 등의 다양한 추가적인 처리가 필요합니다. 즉, 이벤트 자체를 저장하고 관리할 수 있어야합니다. 이벤트 자체를 관리하는 방법 또한 다양합니다. 카프카나 RabbitMQ 같은
메시지 큐를 사용하는 방법도 있고 이벤트 저장소를 만들고 API 혹은 이벤트를 주기적으로 읽어오는 포워더를 만드는 방법도 있습니다.  
그에 대한 것들은 다음에 다루어 보겠습니다.
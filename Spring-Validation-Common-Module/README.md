# @Valid 어노테이션의 동작 원리 #


### 0. @Valid 는 언제 쓰일까? ###
아마도 저를 포함한 많은 개발자들이 스프링을 통해서 개발을 하다보면 값을 검증하는 것 때문에 골치아팠던 기억이 있을 것이라고 
생각합니다. 클라이언트에서 올바른 값이 넘어왔는지, 데이터베이스에 저장하는 값이 올바른 값인지, 비즈니스 로직에서 인수가 제대로 
넘어왔는지등 모든 계층에 아울러 필요한 일이지만, 솔직히 매번하다보면 많이 힘듭니다. 그래서 많은 개발자들이 더 효율적이고 간단하지만 확실한
검증 방법을 고민해왔다고 생각합니다. 

가장 먼저 떠오르는 방법은 if문을 통해서 하는 방법입니다.
``` java 
if(val == '' || val == null) {
    throw new ValidCustomException("value is blank or null.","value");
}
```
간결하고 코드도 복잡하지 않습니다. 검증해야하는 값이 하나일 때는 if문을 통해서 하는 방법도 좋은 것 같습니다.
하지만 회원가입기능처럼 한 번에 많은 값들을 검증해야하는 경우도 많습니다. 검증해야할 값이 많아지면 이렇게 될 것입니다. 
```java
@PostMapping("/member")
public Long saveMember(@RequestBody MemberRequestDto memberRequestDto) {
        if(memberRequestDto.getId() == '' || memberRequestDto.getId() == null) {
            throw new ValidCustomException("id is blank or null.","id");
        }
        if(memberRequestDto.getName() == '' || memberRequestDto.getName() == null) {
            throw new ValidCustomException("name is blank or null.","id");
        }
        if(memberRequestDto.getPhoneNumber() == '' || memberRequestDto.getPhoneNumber() == null) {
            throw new ValidCustomException("phone number is blank or null.","id");
        }
        if(memberRequestDto.getEmail() == '' || memberRequestDto.getEmail() == null) {
            throw new ValidCustomException("email is blank or null.","id");
        }
        return memberService.save(memberRequestDto);
        }
```
코드의 중복도 많고 가독성도 좋지 않습니다. 지금은 공백과 Null 체크만 했지만 실제로는 email 양식이나 전화번호 양식에 대한 검증도 필요
합니다. 그렇게 되면 코드는 더 복잡해집니다. 그렇다면 검증만을 담당하는 메서드를 만들거나 클래스를 만들어보는 것은 어떨까요?

```java
@PostMapping("/member")
public Long saveMember(@RequestBody MemberRequestDto memberRequestDto) {

    saveMemberValidate(memberRequestDto);
    
    return memberService.save(memberRequestDto);
}

private void saveMemberValidate(MemberRequestDto memberRequestDto) {
    if(memberRequestDto.getId() == '' || memberRequestDto.getId() == null) {
        throw new ValidCustomException("id is blank or null.","id");
    }
    if(memberRequestDto.getName() == '' || memberRequestDto.getName() == null) {
        throw new ValidCustomException("name is blank or null.","name");
    }
    if(memberRequestDto.getPhoneNumber() == '' || memberRequestDto.getPhoneNumber() == null) {
        throw new ValidCustomException("phone number is blank or null.","phoneNumber");
    }
    if(memberRequestDto.getEmail() == '' || memberRequestDto.getEmail() == null) {
        throw new ValidCustomException("email is blank or null.","email");
    }
}
```
한결 낫습니다. 하지만 오직 회원가입을 할 때에만 사용하는 메서드(혹은 클래스)이기 때문에 어플리케이션 정책에 따라 회원 정보 수정 혹은 게시글 등록
기능에는 활용할 수 없습니다. 그렇다면 그때마다 검증을 처리할 클래스 혹은 메소드를 생성해야합니다. 
또한, 중복되는 값들도 있을 것입니다. 권한마다 허용되는 값들이 다를 수도 있습니다. 권한마다 기능마다 매번 이렇게 처리하는 방법은 너무 번거롭고 힘이 듭니다.
게다가 이런 검증로직이 여러 계층에 나뉘어져 있어 추적이 어려워지고 결과적으로 어플리케이션을 복잡하게 만들어 유지보수를 어렵게 할 것입니다.
이럴 때 <b> @Valid </b> 를 사용하는 것을 생각해 볼 수 있습니다.

### 1.@Valid 란?  ###
자바 표준 스펙으로 지정되어 있는 Bean Validation 기능 입니다.
Bean Validation은 위키피디아에서 찾아보면 다음과 같이 설명합니다. 

> <p style="text-align: left; color: gray">
> <b> Bean Validation </b> defines a metadata model and API for JavaBean validation 
> — https://en.wikipedia.org/wiki/Bean_Validation <b> JavaBeans </b> are classes that
> encapsulate many objects into a single object (the bean). They are serializable, 
> have a zero-argument constructor, and allow access to properties using getter 
> and setter methods. The name “Bean” was given to encompass this standard,
> which aims to create reusable software components for Java. 
> — https://en.wikipedia.org/wiki/JavaBeans
> </p>

자바 빈의 유효성 검증을 위한 메타데이터 모델과 API에 대한 정의라고 할 수 있습니다.
조금 더 자세히 이해하기 위해서는 자바 빈(JavaBean)과 메타데이터모델이 무엇인지 명확히 해야할 것 같습니다.
뒤의 문장을 살펴보면 자바 빈에 대해서는 다음과 같이 정의하고 있습니다.

> 직렬화 가능하고 매개변수가 없는 생성자를 가지며, Getter 와 Setter Method를 사용하여 프로퍼티에 접근이 가능한 객체

그렇다면 '메타데이터 모델'은 무엇일까요? 메타데이터 모델에 대한 내용은 JSR 303버전에서 찾아볼 수 있습니다.

> Validating data is a common task that occurs throughout an application, from the presentation layer 
> to the persistence layer. Often the same validation logic is implemented in each layer, 
> proving to be time consuming and error prone. To avoid duplication of these validations in each layer, 
> developers often bundle validation logic directly into the domain model, cluttering domain classes 
> with validation code that is, in fact, metadata about the class itself. This JSR defines a metadata model 
> and API for JavaBean validation. The default metadata source is annotations, with the ability to override 
> and extend the meta-data through the use of XML validation descriptors. — JSR 303

Bean Validator의 구현체인 Hibernate Validator의 레퍼런스 가이드에 있는 그림과 같이 이야기 해보겠습니다.


![image](https://user-images.githubusercontent.com/43958570/235728485-3365a07b-6545-41c4-9326-e33fc1d414e6.png)
<p style="color: gray; text-align: center"> https://docs.jboss.org/hibernate/validator/8.0/reference/en-US/html_single/#preface </p>

<i> 
데이터 검증은 애플리케이션의 표현계층부터 영속성 계층까지 전반에 걸쳐 일어나는 공통적인 작업이다. 종종 같은 검증 로직이 각각의 레이어에 구현되는데,
시간이 많이 걸리고 오류를 일으키기 쉽다.
</i> 

처음에 if문을 사용해서 구현했을 때 발견한 문제와 동일한 내용을 이야기하고 있습니다.

![image](https://user-images.githubusercontent.com/43958570/235891734-4d3391d9-8aa9-4d2d-afcf-c67be104419d.png)
<p style="color: gray; text-align: center"> https://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/ </p>
<i>
각 계층의 이런 검증의 중복을 피하기위해 개발자들은 종종 흩어져있는 검증 코드를 포함하는 도메인 클래스들(실제로, 클래스 자신의 메타데이터)을 도메인 모델에 직접 묶는다.  
</i>

제가 이해한 내용은 데이터 검증을 위해 클래스 자신의 메타데이터를 도메인 모델로 묶는 방법을 메타데이터 모델로 표현하고 있다고 생각합니다. 
그리고 자바에서 메타데이터를 표현하는 대표적인 방법은 어노테이션입니다.

정리하면 <b> Bean Validation은 JavaBean의 유효성 검증을 위해 어노테이션을 활용하여 도메인 모델로 묶어서 처리하는 방법</b>이라고 표현할 수 있을것 같습니다. 

Bean Validation은 JSR 303(Bean Validation 1.0)에서 처음 등장하여 JSR 380버전(Bean Validation 2.0)까지 있습니다.
자바 표준 스펙이기때문에 스프링 프레임워크와 관계없이 사용이 가능합니다. 하지만 Bean Validation 2.0 기준으로 Java 8 이상의 버전이 요구됩니다.
3.0 버전도 2020년 8월에 배포되었지만, 이 글을 작성하고 있는 현재기준으로는 아직 JSR에 포함되진 않았습니다. 3.0 버전은 Java 11 이상의 버전이 필요합니다.
스프링부트 2.3 이후부터는 이용하려면 필요한 의존성을 추가해줘야 합니다. 2.3 이전 버전에서는 spring-boot-starter-web 의존성에 포함되어 있습니다.

``` groovy
<dependency> 
     <groupId>org.springframework.boot</groupId> 
     <artifactId>spring-boot-starter-validation</artifactId> 
 </dependency>
 
or
 
implementation 'org.springframework.boot:spring-boot-starter-validation'
```
spring-boot-starter-validation 에 포함된 hibernate-validatior가 Jakarta Bean Validation(Bean Validation)의 구현체입니다.
Bean Validation은 실제로 동작하는 코드가 아니라 명세이기 때문에 애플리케이션에 적용하기 위해서는 이를 구현한 코드가 필요합니다.
hibernate-validatior 만 추가해주어도 Jakarta Bean Validation 의존성도 함께 추가가 됩니다.

### 2. Jakarta Validation 동작원리 ###

![image](https://user-images.githubusercontent.com/43958570/235902562-acbe005b-13c5-4ac8-887e-629e2929f673.png)

기본 API로 제공되는 어노테이션들입니다. 여기 있는 어노테이션들이 클래스의 필드에 선언되면 해당 필드에 제약조건이 적용되어 허용되는 값이 제한됩니다.
이 중 하나를 열어봅시다.

![image](https://user-images.githubusercontent.com/43958570/235904910-fc4e82f7-2462-41ea-8c3f-fbb70aacb7a1.png)

많은 내용이 있지만 봐야하는 곳은 @Constraint 어노테이션이 선언된 곳입니다. 여기에 구현체 정보가 들어가고 검증하는 형태로 동작합니다.
자세한 확인을 위해 Constraint 어노테이션의 내용을 열어보겠습니다.

![image](https://user-images.githubusercontent.com/43958570/235906045-a9c08d02-b60e-40ba-b6d4-2c442ea124e0.png)

구현체의 조건에 대해서 설명하고 있습니다. validateBy의 구현체의 조건은 ConstraintValidator 클래스를 상속해야합니다.

![image](https://user-images.githubusercontent.com/43958570/235909121-d2e42f53-758e-4620-a069-8a1df9001739.png)

이제 ConstraintValidator의 구현체를 찾아야 합니다. 
하지만 Jakatra Bean Validator는 명세이기 때문에 구현체는 이곳에 존재하지 않습니다. 그렇다면 구현체는 어디에 있을까요? 
위에서 이야기한것처럼 SpringBoot에서 Jakarta Bean Validation의 구현체로서 사용하는 hibernate-validatior에 있습니다.
아까 보던 @Email 어노테이션의 구현체는 org.hibernate.validator.internal.constraintvalidators.bv 패키지에서 찾을 수 있었습니다.

![image](https://user-images.githubusercontent.com/43958570/235909438-dfcd8865-a050-4b38-98b7-16fbc922ff69.png)

![image](https://user-images.githubusercontent.com/43958570/235909567-83bb25e6-4484-4ab8-bebe-91340e97924a.png)

EmailValidator 클래스는 AbstractEmailValidator 클래스를 상속하고 있습니다. AbstractEmailValidator클래스를 살펴봅시다.
드디어 ConstraintValidator를 구현한 AbstractEmailValidator 클래스를 찾았습니다. 여기에 Email 검증로직 코드가 있고 구현된 isValid 
메소드를 구현하여 검증로직을 만드는 방식입니다.
그리고 org.hibernate.validator.internal.metadata.core 패키지 아래의 ConstraintHelper 클래스에서 jakarta validation 어노테이션과 
검증로직을 구현한 구현체와 연결시켜줍니다.

![image](https://user-images.githubusercontent.com/43958570/235910920-b1573ba3-e724-4244-bce9-856eaf2c5848.png)

putBuiltinConstraint 메서드의 두 번째 파라미터의 Email.class는 jakarta validation의 어노테이션입니다.
거의 다 왔습니다. 이제 Spring에서 @Email 어노테이션이 어떻게 동작하는지 확인해보겠습니다.

### 3. Spring 에서의 Bean Validation 동작원리 ###
 
@Email 어노테이션을 활용하기 위해서 회원가입을 처리하는 코드를 작성해보겠습니다.

<div style="color: gray">MemberRequestDto</div>

![image](https://user-images.githubusercontent.com/43958570/235911922-0529b3f7-8fb4-4acb-951d-ad90ad2c0c85.png)

<div style="color: gray">MemberController</div>

![image](https://user-images.githubusercontent.com/43958570/235912336-a92c8e1a-9236-48e0-ac1d-f499d0ff8912.png)


회원가입요청이 왔을 때 사용자가 입력한 정보가 MemberRequestDto의 필드의 조건을 만족시키지 못하면 예외를 발생시키는 코드입니다.
테스트코드를 작성하고 의도한대로 동작하는지 확인하기 위해서 우선 유효성검사를 통과하지 못하는 객체를 생성해보겠습니다.

![image](https://user-images.githubusercontent.com/43958570/235913704-2148bd7d-31c5-4f6a-b725-536a70d47ca5.png)
![image](https://user-images.githubusercontent.com/43958570/235914082-f0b02967-878f-46d6-99f3-e760222060bc.png)

테스트를 통과하면 안되지만 아무런 문제없이 테스트를 통과합니다. 뭐가 문제일까요?
이 문제의 답을 얻기 위해서는 Spring에서 @Valid 어노테이션의 동작원리를 알아야합니다.

![image](https://user-images.githubusercontent.com/43958570/235911556-e5868732-4feb-45e5-b84c-6816dff38449.png)

Spring에서는 클라이언트의 요청은 Dispatcher Servlet이 받아서 컨트롤러를 호출합니다. 여기서 Dispatcher Servlet이 다음과 같은 과정을
거쳐 유효성 검사를 진행하게 됩니다.

1. 클라이언트에서 요청발생
2. Dispatcher Servlet이 요청을 처리할 컨트롤러 탐색
3. RequestResponseBodyMethodProcessor 클래스의 resolverArgument 메소드 호출
   ![1](https://user-images.githubusercontent.com/43958570/235920061-2f252051-7ecb-4711-a173-298a77338992.png)

4. AbstractMessageConverterMethodArgumentResolver 클래스의 validateIfApplicable 메소드 호출
   ![image](https://user-images.githubusercontent.com/43958570/235920234-e0bf87e0-44ae-4607-a171-2a1696179e93.png)
5. ValidationAnnotationUtils 클래스의 determineValidationHints
6. 어노테이션이 java.validation.Valid 인지, Validated인지, Valid 로 시작하는지 확인 후 Object Array 리턴
   ![image](https://user-images.githubusercontent.com/43958570/235920549-073e281d-0472-4ad4-b52c-6a5264fd6e0b.png)
7. DataBinder -> ValidatorAdapter -> SpringValidatorAdapter 를 통해 hibernate.validator의 ValidatorImpl 클래스 호출
8. ValidatorImpl 에서 검증 후 결과 리턴
9. 검증 결과 Errors 에 값이 있을 시 MethodArgumentNotValidException 발생
   ![image](https://user-images.githubusercontent.com/43958570/235921764-b3cf2155-4a34-45d4-b4c0-e96eae140698.png)

스프링에서 @Valid 어노테이션에 대한 처리는 Dispatcher Servlet이 처리하기 때문에 Dispatcher Servlet을 거치지 않고 객체 생성을 하는
테스트는 유효성 검사를 하지 않습니다. 그래서 테스트가 성공했다고 추측할 수 있습니다.
그렇다면, 추측이 맞는지 확인하기 위해서 Dispatcher Servlet을 거쳐서 요청을 처리하는 테스트를 작성해보겠습니다.

![image](https://user-images.githubusercontent.com/43958570/235917099-4fb0188c-e699-4731-b8e9-71b2591d2183.png)
![image](https://user-images.githubusercontent.com/43958570/235917226-d14378a5-ca9b-47bb-9bd2-b8550233b77e.png)

이메일 양식을 지키지 않아 테스트를 통과하지 못했습니다.
이번엔 테스트를 통과하기 위해 이메일 양식을 지켜 테스트를 해보겠습니다.
![image](https://user-images.githubusercontent.com/43958570/235917483-99288b67-fdd3-42ff-b4d5-24c07f3b60e9.png)

잘 통과합니다. 
위에서 유효성 검사 어노테이션이 동작하지 않는 이유는 컨트롤러를 거쳐 처리하는 요청이 아니기때문이라는 추측이 맞다는 것이 증명되었습니다.


### 4. 정리 ###
지금까지 유효성 검사를 위해 자주 사용되는 @Valid 어노테이션이 언제 필요한지, 무엇인지, 어떻게 동작하는지에 대해서 알아보았습니다.
처음에는 자주 사용하는 코드가 어떻게 동작하는지 궁금해서 시작했는데, 계속 꼬리에 꼬리를 물고 이어지다보니 굉장히 길어진 것 같습니다.
그래도 무언가 큰 것(?)을 하나 끝냈다는 작은 성취감이 있어서 만족스럽습니다. 제가 정리한 내용이 다른 누군가에게도 작은 도움이 되면 좋겠습니다.
긴 글 봐주셔서 감사합니다.
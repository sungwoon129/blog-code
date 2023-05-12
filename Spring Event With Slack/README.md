## Spring Event를 활용한 Slack 메시지 전송 ##

### 프로젝트 소개 ###

Spring Event 기능을 활용해서 주문 시스템에서 주문이 발생하면, Slack에 '주문알람' 메시지가 전송되는  
기능을 구현한 프로젝트입니다.
REST API 만으로 구현했을 때 도메인계층에서 로직이 섞이는 부분이 있어서 Spring Event를 활용하면 로직이  
도메인 계층에서 섞이지 않고, 주문했을 때 문자메시지를 발송하는 기능과 같은 추가기능을 필요로 할 때도 확장이
용이할 것 같다는 생각에 구현해보았습니다.

### 요구사항 ###
+ 상품 기능
    + 상품 등록
+ 주문 기능
    + 상품 주문

<br>

### UML ###

![Spring Event EXAMPLE UML drawio](https://github.com/sungwoon129/blog-code/assets/43958570/21180f8a-1311-4700-b8af-96ed600f5c43)


<br>


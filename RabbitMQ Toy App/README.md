## RabbitMQ 학습 목적의 어플리케이션 ##

RabbitMQ 학습목적으로 RabbitMQ 공식문서의 tutorial을 실습한 프로젝트입니다.
이 튜토리얼은 rabbitMQ가 설치되어있음을 가정하며, rabbitMQ 프로세스가 localhost 환경과 기본 포트번호인 5672를 사용한다고 가정합니다.
저는 docker 를 활용하여 rabbitMQ 이미지를 가진 컨테이너 환경에 rabbitMQ를 설치하고 진행하였습니다.

### step 1 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/e457192e-8e12-4c21-b28c-3c79c57e0b0d)

가장 기초적인 구조를 가진 아키텍쳐(퍼블리셔 - 브로커 - 컨슈머 ) : Hello World 메시지 전송

### step 2 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/ab790f21-f26a-4c4f-8f32-7d5e3c8a0c8b)

Work Queue : acknowlegement 와 prefetchCount를 사용한 작업 큐구현, durability 옵션으로 RabbitMQ 재시작이 발생해도
task를 잃어버리지 않도록 함

### step 3 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/90cf3525-ae25-41ee-a9bd-ffb596426a7e)

subscribe-publisher 패턴으로 잘 알려진 구조를 메시지 큐로 구현한 예제. Producer가 broadcast 하는 모든 메시지를 모든 consumer가 구독하고 있는 구조. 
step 2까지는 퍼블리셔가 바로 queue에 메시지를 전달했지만, 사이에 exchange를 두어 exchange가 routing해주는 형태
현재 예제에서는 모든 consumer가 메시지를 소비하므로 별도의 routing을 하지 않음

### step 4 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/985d0cb5-4593-4e64-8655-dccb3d4147af)

step 3에서는 exchange를 fanout 방식으로 만들어서 모든 queue에 메시지를 발송했지만, 이번엔 direct 방식으로 만들고 binding key를 통해 routing하는 형태를 구현.
producer에서 발행한 메시지의 routing key와 Consumer에서 구독하고 있는 queue의 binding key가 일치할 경우에만 메시지를 queue에 버퍼링하고 어느 queue와도 일치하지 않는 
routing key를 가진 메시지는 버림

### step 5 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/adef5f39-7e6f-4013-adfa-3b86045f7b6c)

step 4에서는 전달하는 message의 routing key와 queue의 binding key가 1:1 매칭이되는 'direct exchange'를 다루었지만,
step 5에서는 여러 개의 routing key를 다루는 'topic exchange'를 다룬다. 'topic exchange'는 최대 255byte 길이의 문자열을 지원하며 단어들 사이는 '.'으로 구분한다.
'*'은 1개의 단어를 의미하고 '#'은 0개 혹은 그 이상의 단어를 의미한다. *과# 을 활용해서 binding key에 다양한 조건을 줄 수 있고 수신하는 큐를 선택할 수 있다.


### step 6 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/8c0856bf-2164-45ee-8595-79510ceeaf2d)

원격 서버에 요청을 보내고, 원격 서버에서 처리를 하고 응답을 반환하는 구조를 구현한 예제. 일반적인 RPC 형태와 동일하다.
클라이언트가 요청을 할 때, 반환할 큐의 정보와 반환받은 데이터가 요청한 데이터와 매칭이 되는지 확인하기 위한 correlationId 를 함께 AMQP.BasicProperties 클래스에 담아 전송한다.
RPC는 요청과 응답이 동기적으로 처리되기 때문에 클라이언트는 요청을 보내고 응답을 받을 때까지 다른일을 하지 못하기 때문에 예제에서는 다루지않은
문제들에 대한 고민이 필요하다. 구체적으로 응답서버가 죽었을 때나 응답서버가 너무 느려 timeout이 발생한다거나 응답서버에서 예외가 발생하는 등의 문제가 존재한다.

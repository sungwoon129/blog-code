## RabbitMQ 학습 목적의 어플리케이션 ##

RabbitMQ 학습목적으로 RabbitMQ 공식문서의 tutorial을 실습한 프로젝트입니다. 

### step 1 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/e457192e-8e12-4c21-b28c-3c79c57e0b0d)

가장 기초적인 구조를 가진 아키텍쳐(퍼블리셔 - 브로커 - 컨슈머 ) : Hello World 메시지 전송

### step 2 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/ab790f21-f26a-4c4f-8f32-7d5e3c8a0c8b)

Work Queue : acknowlegement 와 prefetchCount를 사용한 작업 큐구현, durability 옵션으로 RabbitMQ 재시작이 발생해도
task를 잃어버리지 않도록 함

### step 3 ###
![image](https://github.com/sungwoon129/blog-code/assets/43958570/90cf3525-ae25-41ee-a9bd-ffb596426a7e)

subscribe-publisher 패턴으로 잘 알려진 구조를 메시지 큐로 구현한 예제. Producer가 broadcast 하는 모든 메시지를 모든 consumer가 구독하고 있는 구조. step 2까지는 퍼블리셔가 바로 queue에 메시지를 전달했지만, 사이에 exchange를 두어 exchange가 routing해주는 형태
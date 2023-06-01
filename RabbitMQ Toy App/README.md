## RabbitMQ 학습 목적의 어플리케이션 ##


### step 1 ###
가장 기초적인 구조를 가진 아키텍쳐(퍼블리셔 - 브로커 - 컨슈머 ) : Hello World 메시지 전송

### step 2 ###
Work Queue : acknowlegement 와 prefetchCount를 사용한 작업 큐구현, durability 옵션으로 RabbitMQ 재시작이 발생해도
task를 잃어버리지 않도록 함
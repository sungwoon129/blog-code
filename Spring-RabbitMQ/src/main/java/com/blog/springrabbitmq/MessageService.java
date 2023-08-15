package com.blog.springrabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageService {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;


    private final RabbitTemplate rabbitTemplate;


    public void sendMessage(MessageDto messageDto) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, messageDto);
        System.out.println("send!");
    }

    @RabbitListener(queues = "sample.queue")
    public void receiveMessage(MessageDto messageDto) {
        System.out.println( messageDto.toString());
        System.out.println("메시지 consume 성공");
    }
}

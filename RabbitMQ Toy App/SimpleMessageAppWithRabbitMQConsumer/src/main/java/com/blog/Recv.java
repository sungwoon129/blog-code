package com.blog;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv {

    private final static String QUEUE_NAME = "task-queue";
    public static void main(String[] args) {

        int TEST_qos = 1;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try {
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            channel.basicQos(TEST_qos);

            DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + message + "'");

                try {
                    doWork(message);
                } finally {
                    System.out.println(" [x] Done");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            });

            boolean autoAck = true;
            channel.basicConsume(QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});
        } catch (IOException | TimeoutException exception) {
            exception.printStackTrace();
        }

    }

    private static void doWork(String message) {
        int TEST_TIME = 1000;
        try {
            Thread.sleep(TEST_TIME);
        } catch (InterruptedException _ignored) {
            Thread.currentThread().interrupt();
        }

    }
}



package com.blog;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;


public class Send {

    private final static String QUEUE_NAME = "task-queue";
    public static void main(String[] args) {
        int TEST_MESSAGE = 10;

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()){

            for(int i=0; i < TEST_MESSAGE; i++) {
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                String message = "Hello World!" + i;
                channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


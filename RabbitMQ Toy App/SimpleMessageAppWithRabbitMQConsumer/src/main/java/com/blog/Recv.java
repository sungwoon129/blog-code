package com.blog;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class Recv {

    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    private static final String RPC_QUEUE_NAME = "rpc_queue";
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
        channel.queuePurge(RPC_QUEUE_NAME);
        channel.basicQos(1);

        System.out.println(" [x] Awaiting RPC requests");


        DeliverCallback deliverCallback = ((consumerTag, delivery) -> {
            AMQP.BasicProperties props = new AMQP.BasicProperties()
                                        .builder()
                    .correlationId(delivery.getProperties().getCorrelationId())
                                        .build();

            String response = "";
            try {
                String message = new String(delivery.getBody(),StandardCharsets.UTF_8);
                int n = Integer.parseInt(message);

                System.out.println(" [.] fib(" + message + ")");
                response += fib(n);
            } catch (RuntimeException e) {
                System.out.println(" [.] " + e);
            } finally {
               channel.basicPublish("", delivery.getProperties().getReplyTo(), props, response.getBytes(StandardCharsets.UTF_8));
            }

        });

        boolean autoAck = true;
        channel.basicConsume(RPC_QUEUE_NAME, autoAck, deliverCallback, consumerTag -> {});


    }

}



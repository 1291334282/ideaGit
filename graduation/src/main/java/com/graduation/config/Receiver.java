package com.graduation.config;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {

    @RabbitHandler
    @RabbitListener(queues = "immediate_queue_test1")
    public void immediateProcess(String message) {
        System.out.println("Receiver" + message);
    }
}
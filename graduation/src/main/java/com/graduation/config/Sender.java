package com.graduation.config;

import java.util.Date;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;
    public void send() {
        String message =  "message" + new Date();
        System.out.println("Sender  " + message);
        rabbitTemplate.convertAndSend("immediate_exchange_test1", "immediate_routing_key_test1", message);
    }
}
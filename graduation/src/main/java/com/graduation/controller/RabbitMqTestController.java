package com.graduation.controller;

import com.graduation.config.Sender;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbit")
@Api(tags = "rabbitmq测试")
@CrossOrigin
public class RabbitMqTestController {
    @Autowired
    Sender sender;

    @PostMapping("/send")
    public String send() {
        sender.send();
        return "success";
    }
}

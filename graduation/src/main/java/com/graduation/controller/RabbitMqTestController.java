package com.graduation.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.graduation.config.Sender;
import com.graduation.config.Task;
import com.graduation.entity.Area;
import com.graduation.mapper.AreaMapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.Future;

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

    @Autowired
    private Task task;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/test")
    public String test() throws Exception {
        long start = System.currentTimeMillis();

        Future<String> task1 = task.doTaskOne();
        Future<String> task2 = task.doTaskTwo();
        Future<String> task3 = task.doTaskThree();

        while (true) {
            if (task1.isDone() && task2.isDone() && task3.isDone()) {
                // 三个任务都调用完成，退出循环等待
                break;
            }
            Thread.sleep(1000);
        }

        long end = System.currentTimeMillis();

        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
        return "success";
    }

    @PostMapping("/test2")
    public void test2() {
        redisTemplate.opsForValue().set("test", "test");
        System.out.println("test+" + redisTemplate.opsForValue().get("test"));
    }

    @Autowired
    AreaMapper areaMapper;

    @PostMapping("/area")
    public void area(@RequestParam(value = "name") String name, @RequestParam(value = "code") String code) {
        long start = System.currentTimeMillis();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (!StringUtils.isEmpty(code)) {
            queryWrapper.eq("code", code);
        }
        List<Area> list = areaMapper.selectList(queryWrapper);
        long end = System.currentTimeMillis();
        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }

}

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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

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
//        redisTemplate.opsForValue().set("test", "test");
//        System.out.println("test+" + redisTemplate.opsForValue().get("test"));
        Calendar cal = Calendar.getInstance();
        //获取当前年份
        int year = cal.get(Calendar.YEAR);
        String firstDay=year+"-1-01";
        System.out.println(firstDay);
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
//        System.out.println("任务全部完成，总耗时：" + (end - start) + "毫秒");
        areaMapper.createDataBase();
    }

    private static int taskCount = 50;//任务数
    private static AtomicInteger taskCountExecuted;//实际完成任务数

    @PostMapping("/threed")
    public void threed() {
        taskCountExecuted = new AtomicInteger();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                10,//核心线程数
                20,//最大线程数
                5,//非核心回收超时时间
                TimeUnit.SECONDS,//超时时间单位
                new ArrayBlockingQueue<>(30)//任务队列
        );
        System.out.println("总任务数：" + taskCount);
        long start = System.currentTimeMillis();
        //模拟任务提交
        for (int i = 0; i < taskCount; i++) {
            Thread thread = new Thread(() -> {
                try {
                    Thread.sleep(500);//模拟执行耗时
                    System.out.println("已执行" + taskCountExecuted.addAndGet(1) + "个任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            try {
                //注意这里我try起来了，默认拒绝策略会报错
                executor.execute(thread);
            } catch (RejectedExecutionException e) {
                taskCount = executor.getActiveCount() + executor.getQueue().size();
            }
        }
        long end = 0;
        while (executor.getCompletedTaskCount() < taskCount) {
            end = System.currentTimeMillis();
        }
        System.out.println(taskCountExecuted + "个任务已执行,总耗时：" + (end - start));
        executor.shutdown();
    }
    private static int num = 20;
    @GetMapping("/test1")
//    @Lock(key = "test1")
    public void test1() {
        System.out.println("当前线程:" + Thread.currentThread().getName());
        if (num == 0) {
            System.out.println("卖完了");
            return;
        }
        num--;
        System.out.println("还剩余：" + num);
    }
}

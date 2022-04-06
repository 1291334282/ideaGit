package com.graduation.thread;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ThreadL {
    public static void main(String[] args) throws InterruptedException {
//        Thread parentParent = new Thread(() -> {
//            ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
//            threadLocal.set(1);
//            InheritableThreadLocal<Integer> inheritableThreadLocal = new InheritableThreadLocal<>();
//            inheritableThreadLocal.set(2);
//
//            new Thread(() -> {
//                System.out.println("threadLocal=" + threadLocal.get());
//                System.out.println("inheritableThreadLocal=" + inheritableThreadLocal.get());
//            }).start();
//        }, "父线程");
//        parentParent.start();
        //获取日历对象

        Calendar ca = Calendar.getInstance();
//        ca.add(Calendar.DAY_OF_MONTH, -2);
//设置时间格式

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date startDate = ca.getTime();
        String startTime = sdf.format(startDate);
        System.out.println(startTime);
    }
}

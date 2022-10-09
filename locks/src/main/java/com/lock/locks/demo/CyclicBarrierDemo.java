package com.lock.locks.demo;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {

        //主线程
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("召唤神龙~");
        });

        for (int i = 1; i <= 7; i++) {
            //子线程
            int finalI = i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" 收集了第 {"+ finalI+"} 颗龙珠");
                try {
                    cyclicBarrier.await(); //加法计数 等待
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }
}

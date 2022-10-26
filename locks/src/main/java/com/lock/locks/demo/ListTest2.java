package com.lock.locks.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ListTest2 {
    public static void main(String[] args) {

        List<Object> arrayList = Collections.synchronizedList(new ArrayList<>());

        for(int i=1;i<=10;i++){
            new Thread(()->{
                arrayList.add(UUID.randomUUID().toString().substring(0,5));
                System.out.println(arrayList);
            },String.valueOf(i)).start();
        }

    }
}

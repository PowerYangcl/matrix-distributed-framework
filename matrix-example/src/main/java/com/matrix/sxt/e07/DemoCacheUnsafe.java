package com.matrix.sxt.e07;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DemoCacheUnsafe {
    private volatile Map<String , Object> map  = new HashMap<>();
    private  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key , Object value){
        System.out.println(Thread.currentThread().getName() + " 正在写入：" + key + " = " + value);
        try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
        map.put(key , value);
        System.out.println(Thread.currentThread().getName() + " 写入完成：" + key + " = " + value);
    }

    public Object get(String key){
        Object result = map.get(key);
        System.out.println(Thread.currentThread().getName() + " 正在读取：" + key);
        System.out.println(Thread.currentThread().getName() + " 读取完成："  + key + " = " + result);
        return result;
    }

}

package com.matrix.sxt.e07;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DemoCache {
    private volatile Map<String , Object> map  = new HashMap<>();
    private  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public void put(String key , Object value){

        try {
            TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
        map.put(key , value);


    }
}

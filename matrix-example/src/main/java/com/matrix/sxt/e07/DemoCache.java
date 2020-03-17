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
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " 正在写入：" + key + " = " + value);
            try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}
            map.put(key , value);
            System.out.println(Thread.currentThread().getName() + " 写入完成：" + key + " = " + value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.writeLock().unlock();
        }
    }

    public Object get(String key){
        Object result =null;
        lock.readLock().lock();
        try {
            result = map.get(key);
            System.out.println(Thread.currentThread().getName() + " 读取完成：" + key + " = " + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.readLock().unlock();
        }
        return result;
    }

    public  void remove(String key){
    }

    public void clearAll(){
    }
}

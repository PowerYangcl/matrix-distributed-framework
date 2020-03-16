package com.matrix.sxt.e06;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo implements Runnable{
    private Lock lock = new ReentrantLock();

    public void run() {
        sendSms();
    }

    private void sendSms(){
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "  发送短信完成");
            this.sendEmail();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    private void sendEmail(){
        lock.lock();
        try {
            // 线程可以进入任何一个他已经拥有锁，所同步着的代码块
            System.out.println(Thread.currentThread().getName() + "  邮件发送完成");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

}

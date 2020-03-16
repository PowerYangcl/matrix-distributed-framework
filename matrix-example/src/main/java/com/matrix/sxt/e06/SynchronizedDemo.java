package com.matrix.sxt.e06;

public class SynchronizedDemo {

    public synchronized void sendSms(){
        System.out.println(Thread.currentThread().getName() + "  发送短信完成");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName() + "  邮件发送完成");
    }
}

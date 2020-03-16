package com.matrix.sxt.e06;

public class TestDemo {
    public static void main(String[] args) {
        SynchronizedDemo sd = new SynchronizedDemo();

//        new Thread(() -> { sd.sendSms(); } , "T1").start();
//        new Thread(() -> { sd.sendSms(); } , "T2").start();

        ReentrantLockDemo rld = new ReentrantLockDemo();
        Thread thread3 = new Thread(rld, "T3");
        Thread thread4 = new Thread(rld, "T4");

        thread3.start();
        thread4.start();

    }
}

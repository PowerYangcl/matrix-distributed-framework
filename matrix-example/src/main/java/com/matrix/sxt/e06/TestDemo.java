package com.matrix.sxt.e06;

public class TestDemo {
    public static void main(String[] args) {
        SynchronizedDemo sd = new SynchronizedDemo();

        new Thread(() -> { sd.sendSms(); } , "T1").start();;
        new Thread(() -> { sd.sendSms(); } , "T2").start();;
    }
}

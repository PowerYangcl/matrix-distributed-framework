package com.matrix.sxt.e04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class ABADemo {
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    public static void main(String[] args) {
        System.out.println("=== 以下是ABA问题的产生 ===");
        new Thread(()->{
            // 线程-1模拟ABA问题
            atomicReference.compareAndSet(100,101);
            atomicReference.compareAndSet(101,100);
        },"线程-1").start();
        
        new Thread(()->{
            // 暂停1秒 保证线程-1完成ABA
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(atomicReference.compareAndSet(100, 200) + "  " + atomicReference.get());
        },"线程-2").start();
    }
}
























package com.matrix.sxt.e04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class AbaResolveDemo {
    private static AtomicStampedReference<Integer> stampedReference = new AtomicStampedReference<>(100, 1);
    public static void main(String[] args) {
        new Thread(()->{
            int stamp = stampedReference.getStamp();
            String name = Thread.currentThread().getName();
            System.out.println(name + "  第1次版本号" + stamp + " 值是" + stampedReference.getReference());
            // 暂停1秒钟
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

            stampedReference.compareAndSet(100,101, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(name + "  第2次版本号" + stampedReference.getStamp() + " 值是" + stampedReference.getReference());
            stampedReference.compareAndSet(101,100, stampedReference.getStamp(), stampedReference.getStamp() + 1);
            System.out.println(name + "  第3次版本号" + stampedReference.getStamp() + " 值是" + stampedReference.getReference());
        },"线程-3").start();

        new Thread(()->{
            int stamp = stampedReference.getStamp();
            String name = Thread.currentThread().getName();
            System.out.println(name + "  第1次版本号" + stamp + " 值是" + stampedReference.getReference());
            // 保证线程-3完成1次ABA
            try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
            boolean result = stampedReference.compareAndSet(100, 2019, stamp, stamp + 1);
            System.out.println(name + "  是否修改成功：" + result + " 最新版本号" + stampedReference.getStamp());
            System.out.println("最新的值 " + stampedReference.getReference());
        },"线程-4").start();
    }
}

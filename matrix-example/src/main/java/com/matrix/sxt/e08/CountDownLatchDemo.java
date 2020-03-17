package com.matrix.sxt.e08;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchDemo {
    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6 ; i ++){
        	final int sleep = i;
            new Thread(() -> {
            	try {TimeUnit.SECONDS.sleep(sleep);} catch (InterruptedException e) {e.printStackTrace();}
                System.out.println(Thread.currentThread().getName() + "  国，被灭");
                countDownLatch.countDown();
            }, CountryEnmu.foreach(i).getName() ).start();
        }

        new Thread(() -> {
            try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "  一统华夏！");
        }, "秦国" ).start();
        
        new Thread(() -> {
            try { countDownLatch.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println(Thread.currentThread().getName() + "  再次一统华夏！");
        }, "汉朝" ).start();
    }
}











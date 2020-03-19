package com.matrix.sxt.e09;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
public class SynchronousQueueDemo {
	public static void main(String[] args) {
		BlockingQueue<String> blockingQueue = new SynchronousQueue<>();
		// 模拟向队列中放入数据
		new Thread(() -> {
            try {
                System.out.println(Thread.currentThread().getName() + "  放入 1");
                blockingQueue.put("1");
                System.out.println(Thread.currentThread().getName() + "  放入 2");
                blockingQueue.put("2");
                System.out.println(Thread.currentThread().getName() + "  放入 3");
                blockingQueue.put("3");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程-A").start();

		// 线程ABC模拟从队列中取数据
        new Thread(() -> {
            try {
            	TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "  获取" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程-B").start();
        new Thread(() -> {
            try {
            	TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "  获取" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程-C").start();
        new Thread(() -> {
            try {
            	TimeUnit.SECONDS.sleep(5);
                System.out.println(Thread.currentThread().getName() + "  获取" + blockingQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "线程-D").start();
	}
}














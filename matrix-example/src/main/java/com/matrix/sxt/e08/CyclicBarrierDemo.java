package com.matrix.sxt.e08;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
	public static void main(String[] args) {
		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
			System.out.println("所有线程都已到达屏障，开始召唤神龙");  // 所有线程都执行完成后，此处才开始执行计算操作
		});
		
//		CyclicBarrier cyclicBarrier = new CyclicBarrier(7, new Runnable() {
//			public void run() {
//				System.out.println("所有线程都已到达屏障，开始召唤神龙");  // 所有线程都执行完成后，此处才开始执行计算操作
//			}
//		});

		for (int i = 1; i <= 7; i++) {
			final int temp = i;
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName() + "  收集到第" + temp + "颗龙珠");
				try {
					cyclicBarrier.await(); // 先到达屏障的，被阻塞
				} catch (InterruptedException |BrokenBarrierException e) {
					e.printStackTrace();
				}
			},"线程-" + String.valueOf(i) ).start();
		}
		
		
		
		
		
		
	}
}

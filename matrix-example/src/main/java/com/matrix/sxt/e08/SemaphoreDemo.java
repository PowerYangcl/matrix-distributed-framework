package com.matrix.sxt.e08;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo {

	public static void main(String[] args) {
		Semaphore semaphore = new Semaphore(5);  // 模拟5个停车位
		for(int i = 1 ; i <= 10 ; i ++) {  // 模拟10辆车
			final int temp = i * 10;
			new Thread(() -> {
				try {
					semaphore.acquire();
					
					System.out.println(Thread.currentThread().getName() + "  抢到车位");
					try {TimeUnit.SECONDS.sleep( temp );} catch (InterruptedException e) {e.printStackTrace();} 
					System.out.println(Thread.currentThread().getName() + "  停车：" + temp + "秒后离开车位");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}finally {
					semaphore.release();
				}
				
			},"车主-" + String.valueOf(i) ).start();
		}
	}

}

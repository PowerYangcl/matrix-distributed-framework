package com.matrix.sxt.e01;

import java.util.concurrent.TimeUnit;

public class VolatileMain {
	public static void main(String[] args) {
		MyData data = new MyData();
		
		new Thread( () -> {
			System.out.println(Thread.currentThread().getName() + " 进入");
			// 线程等待3秒
			try {TimeUnit.SECONDS.sleep(3);} catch (InterruptedException e) {e.printStackTrace();}
			data.setNumber(10);	// 设置一个新值 10
			System.out.println(Thread.currentThread().getName() + " 更新 number 值为：" + data.getNumber());
		} , "线程A" ).start();
		
		while (data.getNumber() == 0) {
			// main 线程在此处等待，知道number值不在等于0
		}
		
		System.out.println(Thread.currentThread().getName() + " mission is over");
	}
}

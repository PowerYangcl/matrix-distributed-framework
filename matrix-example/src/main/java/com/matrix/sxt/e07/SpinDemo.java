package com.matrix.sxt.e07;

import java.util.concurrent.TimeUnit;

public class SpinDemo {

	public static void main(String[] args) {
		SpinLock lock = new SpinLock();
		new Thread(() -> {
			lock.lock();
			try {TimeUnit.SECONDS.sleep(5);} catch (InterruptedException e) {e.printStackTrace();} // 当前锁占用5秒，模拟该线程执行任务
			lock.unlock();
		} , "T1").start();
		
		try {TimeUnit.SECONDS.sleep(2);} catch (InterruptedException e) {e.printStackTrace();}	// 暂停main线程2秒，确保最先执行线程T1
		
		new Thread(() -> {	
			lock.lock();
			lock.unlock();
		} , "T2").start();
	}
}
























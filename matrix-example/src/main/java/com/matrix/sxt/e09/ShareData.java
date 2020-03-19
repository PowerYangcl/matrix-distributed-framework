package com.matrix.sxt.e09;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description: ShareData.java即为【资源类】
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年3月19日 下午3:24:35 
 * @version 1.0.0.1
 */
public class ShareData {
	
	private int number = 0;
	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	
	public void increment() {
		lock.lock();
		try {
			// 1、判断
			while(number != 0) { // while防止虚假唤醒。if判断的话多余2个线程就会出错
				condition.await();		// 等待，不能生产。调用await方法后操作，线程的锁被“原子级地”释放
			}
			// 2、干活
			number ++;
			System.out.println(Thread.currentThread().getName() + "  增加 number = " + number);
			
			// 3、通知唤醒
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void decrement() {
		lock.lock();
		try {
			// 1、判断
			while(number == 0) {	// while防止虚假唤醒。if判断的话多余2个线程就会出错
				condition.await(); // 等待，不能生产。调用await方法后操作，线程的锁被“原子级地”释放
			}
			// 2、干活
			number --;
			System.out.println(Thread.currentThread().getName() + "  消耗 number = " + number);
			
			// 3、通知唤醒
			condition.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}
















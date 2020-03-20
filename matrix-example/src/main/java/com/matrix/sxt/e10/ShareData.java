package com.matrix.sxt.e10;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ShareData {
	private int number = 1;  // 1/2/3分别代表ABC三个线程。
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	private Condition conditionC = lock.newCondition();
	
	public void printOne() {
		lock.lock();
		try {
			// 1、判断
			while(number != 1) {  
				conditionA.await();
			}
			// 2、干活
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			
			// 3、通知唤醒
			number = 2;
			conditionB.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void printTwo() {
		lock.lock();
		try {
			// 1、判断
			while(number != 2) {  
				conditionB.await();
			}
			// 2、干活
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			
			// 3、通知唤醒
			number = 3;
			conditionC.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
	
	public void printThree() {
		lock.lock();
		try {
			// 1、判断
			while(number != 3) {  
				conditionC.await();
			}
			// 2、干活
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			System.out.println(Thread.currentThread().getName() + " number = " + number);
			
			// 3、通知唤醒
			number = 1;
			conditionA.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			lock.unlock();
		}
	}
}












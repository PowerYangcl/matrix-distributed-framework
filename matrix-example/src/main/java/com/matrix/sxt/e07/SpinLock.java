package com.matrix.sxt.e07;

import java.util.concurrent.atomic.AtomicReference;
/**
 * @description: 手写一个自旋锁
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年3月16日 下午3:14:13 
 * @version 1.0.0.1
 */
public class SpinLock {
	AtomicReference<Thread> atomicReference = new AtomicReference<Thread>();

	public void lock() {
		Thread thread = Thread.currentThread();
		System.out.println(Thread.currentThread().getName() + "  尝试获取锁");
		while ( !atomicReference.compareAndSet(null, thread) ) {  // 期望值是空，那么则设置成自己为当前线程，否则现在已经有其他线程了
			// 开始自旋等待
		}
	}
	
	public void unlock() {
		Thread thread = Thread.currentThread();
		atomicReference.compareAndSet(thread , null); // 如果当前锁用完了，那么置空让其他线程获取锁。
		System.out.println(Thread.currentThread().getName() + "  开始释放锁");
	}
}
































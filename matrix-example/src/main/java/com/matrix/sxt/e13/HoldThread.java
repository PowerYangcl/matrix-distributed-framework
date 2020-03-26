package com.matrix.sxt.e13;

import java.util.concurrent.TimeUnit;

public class HoldThread implements Runnable {
	private String lockA;
	private String lockB;

	public HoldThread(String lockA, String lockB) {
		this.lockA = lockA;
		this.lockB = lockB;
	}

	public void run() {
		synchronized (lockA) {
			System.out.println(Thread.currentThread().getName() + "  自己持有锁：" + lockA + "      尝试获得：" + lockB);

			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			synchronized (lockB) {
				System.out.println(Thread.currentThread().getName() + "  自己持有锁：" + lockB + "      尝试获得：" + lockA);
			}
		}
	}

	public static void main(String[] args) {
		String lockA = "lockA";
		String lockB = "lockB";
		new Thread(new HoldThread(lockA, lockB), "线程-A").start();
		new Thread(new HoldThread(lockB, lockA), "线程-B").start();
	}
}

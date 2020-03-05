package com.matrix.sxt.e02;

public class VolatileMain {
	public static void main(String[] args) {
		MyData data = new MyData();
		
		for(int i = 1 ; i <= 20 ; i ++) {
			new Thread( () -> {
				for(int n = 0 ; n < 1000 ; n ++) {
					data.addNumber();
					data.atomicAdd();  // 调用原子整形
				}
			} , "线程-" + i ).start();
		}
		
		while (Thread.activeCount() > 2) {
			// 等待上面20个线程计算完成，在用Main线程获取最终结果
			Thread.yield();
		}
		// 由于期待值是：20000，但由于volatile并不具有原子性，故这里打印出正确数值的概率极小。
		System.out.println(Thread.currentThread().getName() + " finally number value is : " + data.getNumber());
		System.out.println(Thread.currentThread().getName() + " atomic integer value is : " + data.getAtomicValue());
	}
}

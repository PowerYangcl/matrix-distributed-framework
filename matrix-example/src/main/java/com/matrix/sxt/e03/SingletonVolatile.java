package com.matrix.sxt.e03;

public class SingletonVolatile {
	private static volatile SingletonVolatile instance = null;
	private SingletonVolatile() {
		System.out.println(Thread.currentThread().getName() + " SingletonVolatile私有构造函数实例化");
	}
	// DCL Double check lock 双端捡锁
	public static SingletonVolatile getInstance() {
		if(instance == null) {
			synchronized (SingletonVolatile.class) {
				if(instance == null) {
					instance = new SingletonVolatile();
				}
			}
		}
		return instance;
	}
	
	public static void main(String[] args) {
		for (int i = 1; i <=1000; i++) {
            new Thread(() ->{ SingletonVolatile.getInstance(); } , "当前线程：" + String.valueOf(i) ).start();
        }
	}
}

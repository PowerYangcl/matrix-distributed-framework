package com.matrix.sxt.e09;

public class ShareDateMain {
	public static void main(String[] args) {
		ShareData shareData = new ShareData();
		
		new Thread(() -> {
			for(int i = 1 ; i <= 5 ; i ++) {
				shareData.increment();
			}
        }, "线程-A").start();
		
		new Thread(() -> {
			for(int i = 1 ; i <= 5 ; i ++) {
				shareData.decrement();
			}
        }, "线程-B").start();
	}
}

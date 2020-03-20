package com.matrix.sxt.e10;

public class ShareDateMain {
	public static void main(String[] args) {
		ShareData shareData = new ShareData();
		new Thread(() -> {
			for(int i = 1 ; i <= 5 ; i ++) {
				shareData.printOne();
			}
        }, "线程-A").start();
		
		new Thread(() -> {
			for(int i = 1 ; i <= 5 ; i ++) {
				shareData.printTwo();
			}
        }, "线程-B").start();
		
		new Thread(() -> {
			for(int i = 1 ; i <= 5 ; i ++) {
				shareData.printThree();
			}
        }, "线程-C").start();
	}
}

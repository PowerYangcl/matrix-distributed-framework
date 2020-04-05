package com.matrix.sxt.e15;

import java.util.concurrent.TimeUnit;


public class UnableCreateNewNativeThread {
	public static void main(String[] args) {
		for(int i = 0 ; i < 2048; i ++) {
			System.out.println("i = " + i);
			new Thread(() -> {
            	try {TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);} catch (InterruptedException e) {e.printStackTrace();}
            }, "线程：" + i).start();
		}
	}
}

package com.matrix.sxt.e10;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueResourceMain {
	public static void main(String[] args) {
		BlockingQueueResource resource = new BlockingQueueResource(new ArrayBlockingQueue<String>(10));
		
		new Thread(() -> {resource.producer();}, "厨子巴扎黑").start();
		new Thread(() -> {resource.producer();}, "厨子傻憨黑").start();
		
		new Thread(() -> {resource.consumer();}, "狸花猫吾皇").start();
		for(int i = 1 ; i < 3 ; i ++) {
			new Thread(() -> {resource.consumer();}, "买蛋糕路人-" + i).start();
		}
		// 设置运行5秒钟
		try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
        resource.stop();
	}
}

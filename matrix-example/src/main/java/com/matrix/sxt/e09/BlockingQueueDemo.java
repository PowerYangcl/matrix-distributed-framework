package com.matrix.sxt.e09;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
        	System.out.println("插入第1个元素，状态：" + blockingQueue.offer("a" , 2L , TimeUnit.SECONDS));
            System.out.println("插入第2个元素，状态：" + blockingQueue.offer("b" , 2L , TimeUnit.SECONDS));
            System.out.println("插入第3个元素，状态：" + blockingQueue.offer("c" , 2L , TimeUnit.SECONDS));
            System.out.println("插入第4个元素，状态：" + blockingQueue.offer("d" , 2L , TimeUnit.SECONDS)); // 2秒后无法进入队列则返回false
        	
        	System.out.println("开始移除元素：" + blockingQueue.poll(2L , TimeUnit.SECONDS));		// 返回被移除的对象a
            System.out.println("开始移除元素：" + blockingQueue.poll(2L , TimeUnit.SECONDS));		// 返回被移除的对象b
            System.out.println("开始移除元素：" + blockingQueue.poll(2L , TimeUnit.SECONDS));		// 返回被移除的对象c
        	
        	System.out.println("继续移除第四个元素，返回：" + blockingQueue.poll(2L , TimeUnit.SECONDS)); // 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
    
    
    
    public void main1() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        
        System.out.println(blockingQueue.element());
        System.out.println(blockingQueue.element());

        System.out.println(blockingQueue.remove());		// 返回被移除的对象a
        System.out.println(blockingQueue.remove());		// 返回被移除的对象b
        System.out.println(blockingQueue.remove());		// 返回被移除的对象c
        
        System.out.println(blockingQueue.remove()); // 队列中共三个对象，当移除第四个的时候会抛出异常
    }
    
    public  void main2() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        System.out.println("插入第1个元素，状态：" + blockingQueue.offer("a"));
        System.out.println("插入第2个元素，状态：" + blockingQueue.offer("b"));
        System.out.println("插入第3个元素，状态：" + blockingQueue.offer("c"));
        System.out.println("插入第4个元素，状态：" + blockingQueue.offer("d"));
        
        System.out.println("开始移除元素：" + blockingQueue.poll());		// 返回被移除的对象a
        System.out.println("开始移除元素：" + blockingQueue.poll());		// 返回被移除的对象b
        System.out.println("开始移除元素：" + blockingQueue.poll());		// 返回被移除的对象c
        
        System.out.println("检查队列是否为空，同时返回队首元素：" + blockingQueue.peek()); 
        
        System.out.println("继续移除第四个元素，返回：" + blockingQueue.poll()); // 队列中共三个对象，当移除第四个的时候返回null
    }
    
    public void main3() {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        try {
        	blockingQueue.put("a"); 	// 返回void，sysout无法打印
        	blockingQueue.put("b");
        	blockingQueue.put("c");
//        	blockingQueue.put("d");  	// 此时会一直阻塞
        	
        	System.out.println("开始移除元素：" + blockingQueue.take());		// 返回被移除的对象a
        	System.out.println("开始移除元素：" + blockingQueue.take());		// 返回被移除的对象b
        	System.out.println("开始移除元素：" + blockingQueue.take());		// 返回被移除的对象c
        	
        	System.out.println("继续移除第四个元素，返回：" + blockingQueue.take()); // 队列中共三个对象，当移除第四个的时候一直阻塞，知道队列中有第四个元素进入
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    }
}

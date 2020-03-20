package com.matrix.sxt.e10;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

public class BlockingQueueResource {
	
	private volatile boolean flag = true;	// 生产者与消费者的方法运行标识，同生共死，默认开启
	private AtomicInteger atomicInteger = new AtomicInteger();		// 默认值是0
	private BlockingQueue<String> blockingQueue = null;		// 模拟放蛋糕的柜子

	public BlockingQueueResource(BlockingQueue<String> blockingQueue) {
		this.blockingQueue = blockingQueue;
	}
	
	public void producer() {
		String producer = "";
		boolean offer;
		String name = Thread.currentThread().getName();
		try {
			while(flag) {		// 准备执行生产活动
				producer = atomicInteger.incrementAndGet() + "";
				offer = blockingQueue.offer(producer, 1L, TimeUnit.SECONDS);
				if(offer) {
					System.err.println(name + "  做完了第【" + producer + "】个蛋糕，放入柜台成功");
				}else {
					System.err.println(name + "  做完了第【" + producer + "】个蛋糕，放入柜台失败！！！！！");
				}
				TimeUnit.SECONDS.sleep(1);
//				TimeUnit.MILLISECONDS.sleep(200);
			}
			System.err.println(name + "  producer 生产蛋糕的行为已经停止");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void consumer() {
		String result = "";
		String name = Thread.currentThread().getName();
		try {
			while(flag) {
				result = blockingQueue.poll(3L, TimeUnit.SECONDS);
				if(StringUtils.isBlank(result)) {
//					flag = false;
					System.out.println(name +"  排队超过2m没有取到蛋糕，不再等待去别的店买了");
					return;
				}
				System.out.println(name + " 从柜台中买走了第【" + result + "】个蛋糕");
//				TimeUnit.SECONDS.sleep(1);
			}
			System.out.println("蛋糕店不做蛋糕了，" + name + "  已经离开。");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		flag = false;
		System.out.println("时间到，活动停止。当前柜台里还有：" + blockingQueue.size() + "个蛋糕");
	}
}






































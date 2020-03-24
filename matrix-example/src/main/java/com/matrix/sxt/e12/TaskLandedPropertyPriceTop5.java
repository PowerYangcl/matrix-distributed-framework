package com.matrix.sxt.e12;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

// 模拟地产信息前5名
public class TaskLandedPropertyPriceTop5 implements Callable<JSONObject> {
	private CountDownLatch countDownLatch;

	public TaskLandedPropertyPriceTop5(CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
	}


	@Override
	public JSONObject call() throws Exception {
		JSONObject result = new JSONObject();
		try { TimeUnit.SECONDS.sleep(12); } catch (InterruptedException e) { e.printStackTrace(); }  // 模拟查库耗时12秒
		result.put("data", "地产信息前5名接口查库耗时12秒");
		this.countDownLatch.countDown(); // 计数器减一
		return result;
	}

}

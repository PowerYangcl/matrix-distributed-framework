package com.matrix.sxt.e11;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.alibaba.fastjson.JSONObject;
import com.matrix.dao.ITcLandedPropertyMapper;

// 模拟地产信息前5名
public class TaskLandedPropertyPriceTop5 implements Callable<JSONObject> {
	private CountDownLatch countDownLatch;
	private ITcLandedPropertyMapper tcLandedPropertyMapper;
	
	public TaskLandedPropertyPriceTop5(ITcLandedPropertyMapper tcLandedPropertyMapper , CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.tcLandedPropertyMapper = tcLandedPropertyMapper;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONObject result = new JSONObject();
		try { TimeUnit.SECONDS.sleep(12); } catch (InterruptedException e) { e.printStackTrace(); }  // 模拟查库耗时12秒
		result.put("data", "地产信息前5名接口查库耗时2秒");
		this.countDownLatch.countDown(); // 计数器减一
		return result;
	}

}

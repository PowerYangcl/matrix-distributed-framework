package com.matrix.sxt.e11;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.dao.ITcLandedPropertyMapper;
import com.matrix.dao.IUserDemoMapper;

// 通常一个APP的首页会调用很多不相干的信息
public class AppHomePageService extends BaseClass{
	
	@Inject		// 模拟要访问的数据库
	private ITcLandedPropertyMapper tcLandedPropertyMapper;
	@Inject  // 模拟要访问的数据库
	private IUserDemoMapper userDemoMapper;
	
	public JSONObject homePageInfo(JSONObject dto) {
		JSONObject result = new JSONObject();
		
		CountDownLatch countDownLatch = new CountDownLatch(3); 	// 初始一个线程数量，这里启动三个线程作为示例
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Task1001Position callable1 = new Task1001Position("lat", "lng" , countDownLatch);
		Future<JSONObject> callable1Task =  executor.submit(callable1);
		
		TaskUserDemoTop5 callable2 = new TaskUserDemoTop5(userDemoMapper , countDownLatch);
		Future<JSONObject> callable2Task =  executor.submit(callable2);
		
		TaskLandedPropertyPriceTop5 callable3 = new TaskLandedPropertyPriceTop5(tcLandedPropertyMapper , countDownLatch);
		Future<JSONObject> callable3Task =  executor.submit(callable3);
		
		
		try {
			countDownLatch.await();
			result.put("callable1Task", callable1Task.get().getString("data"));
			result.put("callable2Task", callable2Task.get().getString("data"));
			result.put("callable3Task", callable3Task.get().getString("data"));
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally {
			executor.shutdown();
		}
		
		System.out.println("$$$$$$$$$$$$ = " + result.toJSONString());
		return result;
	}
}













































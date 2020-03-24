package com.matrix.sxt.e12;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.alibaba.fastjson.JSONObject;

// 通常一个APP的首页会调用很多不相干的信息
public class AppHomePageService{
	
	
	public JSONObject homePageInfo(JSONObject dto) {
		JSONObject result = new JSONObject();
		
		CountDownLatch countDownLatch = new CountDownLatch(3); 	// 初始一个线程数量，这里启动三个线程作为示例
		
		ExecutorService executor = Executors.newCachedThreadPool();
		
		Task1001Position callable1 = new Task1001Position("lat", "lng" , countDownLatch);
		Future<JSONObject> callable1Task =  executor.submit(callable1);
		
		TaskUserDemoTop5 callable2 = new TaskUserDemoTop5(countDownLatch);
		Future<JSONObject> callable2Task =  executor.submit(callable2);
		
		TaskLandedPropertyPriceTop5 callable3 = new TaskLandedPropertyPriceTop5(countDownLatch);
		Future<JSONObject> callable3Task =  executor.submit(callable3);
		
		
		try {
			 long startTimeMillis = System.currentTimeMillis();
			JSONObject json = callable1Task.get(15, TimeUnit.SECONDS);  // TimeoutException，5秒后超时
			System.out.println("json = " + json);
			countDownLatch.await();
			result.put("callable1Task", callable1Task.get().getString("data"));
			result.put("callable2Task", callable2Task.get().getString("data"));
			result.put("callable3Task", callable3Task.get().getString("data"));
			
			System.out.println(result.toJSONString());
			
			long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
			System.out.println("请求耗时：" + execTimeMillis/1000 + " 秒");
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}finally {
			executor.shutdown();
		}
		return result;
	}
	
	public static void main(String[] args) {
		AppHomePageService service = new AppHomePageService();
		service.homePageInfo(null);
	}
}













































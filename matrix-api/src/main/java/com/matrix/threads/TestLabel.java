package com.matrix.threads;


import com.alibaba.fastjson.JSONObject;

/** @description: 
 *
 * @author wanghao
 * @date 2019年10月21日 上午11:22:18 
 * @version 1.0.0.1
 */
public class TestLabel implements Runnable{
	private int  time;
	
	public TestLabel(int time) {
		super();
		this.time = time;
	}

	public void run() {
		JSONObject data = new JSONObject();
		data.put("cid", time);
		
//		try {
//			System.out.println(Thread.currentThread().getName() + " = " + this.time); 
//			Thread.sleep(this.time*1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		long startTimeMillis = System.currentTimeMillis();
		JSONObject doRequest = PaymentRequestSupport.doRequest("PRODUCT-LABEL-LIST-PROCESSOR", data);
		long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
		
		System.out.println("								  标签数据:-----------"+doRequest.getString("data") + " 耗时：" + execTimeMillis);
	}
}










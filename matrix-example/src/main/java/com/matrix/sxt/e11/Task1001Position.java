package com.matrix.sxt.e11;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import com.alibaba.fastjson.JSONObject;

/**
 * @description: Task[表示这是一个多线程任务] 1001[表示调用了第三方的1001接口] Position[表示位置信息]
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2020年3月24日 下午2:01:03 
 * @version 1.0.0.1
 */
public class Task1001Position implements Callable<JSONObject> {
	private CountDownLatch countDownLatch;
	private String lat = "";
	private String lng = "";
	public Task1001Position(String lat, String lng , CountDownLatch countDownLatch) {
		this.countDownLatch = countDownLatch;
		this.lat = lat;
		this.lng = lng;
	}

	@Override
	public JSONObject call() throws Exception {
		JSONObject result = new JSONObject();
		// TODO 调用第三方接口，获取位置信息
		this.lat = "123899012.123";
		this.lng = "324124.213123";
		try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }  // 模拟查库耗时10秒
		result.put("data", "北京市通州区梨园镇天使之城小区");
		this.countDownLatch.countDown(); // 计数器减一
		return result;
	}
}




































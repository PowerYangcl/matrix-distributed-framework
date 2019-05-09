package com.matrix.quartz.job;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.alibaba.fastjson.JSONObject;


/**
 * @description: 这是第三个定时任务的测试类，用于验证定时任务模块是否稳定
 * 
 * @rglist matrix-quartz-test
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月28日 下午2:16:04 
 * @version 1.0.0
 */
public class JobForTestThree extends RootJob {	 

	private static Logger logger = Logger.getLogger(JobForTestThree.class);
	
	public JSONObject doExecute(JobExecutionContext context) {
		JSONObject result = new JSONObject();
		result.put("status", "success");					// 必须包含的两个字段
		result.put("msg", "定时任务执行成功!");    // 必须包含的两个字段
		
		String rglist = "***************** 所属任务组：matrix-quartz-test";
		this.getLogger(logger).logInfo(999990001 , "@ JobForTestThree.java is running" , rglist); 
		
		return result;
	}

}

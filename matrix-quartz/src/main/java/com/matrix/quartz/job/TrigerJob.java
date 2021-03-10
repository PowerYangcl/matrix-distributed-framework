package com.matrix.quartz.job;

import org.apache.commons.lang.ClassUtils;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.matrix.pojo.entity.JobInfo;

/**
 * @description: 当前定时任务执行完成后会触发他所关联的其他定时任务
 *
 * @author Yangcl
 * @date 2018年12月19日 上午10:38:06 
 * @version 1.0.0.1
 */
public class TrigerJob extends Thread {
	private String jobJson;
	
	public TrigerJob(String jobJson) {
		this.jobJson = jobJson;
	}

	public void run() {
		try {
			JobInfo info = JSONObject.parseObject(jobJson, JobInfo.class);
			@SuppressWarnings("unchecked")
			Class<RootJob> jobClass = ClassUtils.getClass(info.getJobClass());
			RootJob rootJob = jobClass.newInstance();
			rootJob.setJobInfo(info);
			rootJob.execute(null);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JobExecutionException e) { 
			e.printStackTrace();
		}
	}

}






























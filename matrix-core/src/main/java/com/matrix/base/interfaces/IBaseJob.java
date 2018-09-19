package com.matrix.base.interfaces;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * @description: 定时任务基础接口
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午4:37:54 
 * @version 1.0.0
 */
public interface IBaseJob extends Job {

	/**
	 * @description: 调用执行定时任务
	 * 
	 * @param context
	 * @author Yangcl 
	 * @date 2016年9月29日 下午4:38:14 
	 * @version 1.0.0.1
	 */
	public void doExecute(JobExecutionContext context);
	  
}

























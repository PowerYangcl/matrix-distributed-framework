package com.matrix.base.interfaces;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.alibaba.fastjson.JSONObject;

/**
 * @description: 定时任务基础接口|具体到每一个定时任务会实现这个抽象方法，来处理其自身特有的业务逻辑
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
	 * 	返回值中包含必要参数：status和msg
	 * 			status：success | error | exception；分别代表成功、失败和异常
	 * 
	 * @param context 可以为null
	 * @author Yangcl 
	 * @date 2016年9月29日 下午4:38:14 
	 * @version 1.0.0.1
	 */
	public JSONObject doExecute(JobExecutionContext context);
	  
}

























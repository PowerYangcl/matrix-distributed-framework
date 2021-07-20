package com.matrix.quartz.job;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IJobExecLogMapper;
import com.matrix.pojo.entity.JobExecLog;


/**
 * @description: 记录当前定时任务执行后的结果
 *
 * @author Yangcl
 * @date 2018年12月19日 下午7:13:58 
 * @version 1.0.0.1
 */
public class JobLog extends BaseClass implements Runnable {	 
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IJobExecLogMapper jobExecLogMapper;
	private JSONObject result;

	public JobLog(JSONObject result) {
		this.result = result;
	}
	
	public void run() {
		if(StringUtils.isBlank(result.getString("status"))) {
			return;
		}
		JobExecLog e = new JobExecLog();
		String value = launch.loadDictCache(DCacheEnum.SysJob , "SysJobInit").get(result.getJSONObject("jobInfo").getString("jobName")); 
		JSONObject o = JSONObject.parseObject(value);
		e.setJobName(o.getString("jobName"));
		e.setJobTitle(o.getString("jobTitle"));
		e.setIp(o.getString("ip"));
		e.setRunGroupId(o.getLong("runGroupId"));
		e.setRunGroupName(o.getString("groupName"));
		e.setBeginTime(o.getDate("beginTime"));
		e.setEndTime(o.getDate("endTime"));
		e.setRemark(result.toJSONString());
		e.setCreateTime(new Date());
		e.setCreateUserId(1L);
		e.setCreateUserName("system-job");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(1L);
		e.setUpdateUserName("system-job"); 
		
		if(result.getString("status").equals("exception")) {
			e.setStatus("exception");
			e.setRemark(result.getString("msg"));
		}else if(result.getString("status").equals("error")) {
			e.setStatus("error");
		}else if(result.getString("status").equals("success")) {
			e.setStatus("success");
		}
		jobExecLogMapper.insertSelective(e);
	}
}
































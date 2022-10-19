package com.matrix.security;

import org.apache.commons.lang.ClassUtils;
import org.quartz.JobExecutionException;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.quartz.job.RootJob;
import com.matrix.quartz.support.JobSupport;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 主动触发定时任务|当定时任务在一个节点处于关闭状态的
 * 			时候，此处会将所有定时任务加入轮询器，同时激活
 *
 * @author Yangcl
 * @date 2019年1月9日 下午3:26:31 
 * @version 1.0.0.1
 */
@Slf4j
public class JobExecGuard extends BaseClass implements IBaseExecute {

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	public String execute(String json) {
		try {
			JobInfo info = null;
			JobInfoDto dto = JSONObject.parseObject(json , JobInfoDto.class);
			if(dto.getGuardType().equals("triger")) {	// 开始手动触发这个定时任务
				info = jobInfoMapper.find(dto.getId());
				@SuppressWarnings("unchecked")
				Class<RootJob> jobClass = ClassUtils.getClass(info.getJobClass());
				RootJob rootJob = jobClass.newInstance();
				rootJob.setJobInfo(info);
				rootJob.execute(null);
			}
			
			if(dto.getGuardType().equals("add")) {
				JobSupport.getInstance().addJob(info);
			}
			log.info(this.getInfo(200010005, info.getJobName() , info.getJobTitle() , info.getJobTriger()));  // 200010005=开始加载任务: {0}，定时任务标题: {1}，任务执行周期为: {2}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | JobExecutionException ex) {
			log.error("JobExecGuard.execute exception ", ex);
			return "JobExecGuard exception !";
		}
		return "执行成功!";
	}

}





























package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.JobInfoView;

public interface IJobInfoMapper extends IBaseDao<Long, JobInfo, JobInfoDto, JobInfoView>{
	
	public Integer updateSelectiveByJobName(JobInfo info);

	public JobInfo findByName(String jobName);

	/**
	 * @description: 暂停所有定时任务 
	 *
	 * @param e
	 * @author Yangcl
	 * @date 2018年12月25日 下午5:40:04 
	 * @version 1.0.0.1
	 */
	public Integer pauseAll(JobInfo e);
}
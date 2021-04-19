package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.entity.JobInfo;

public interface IJobService {                              
	
	/**
	 * @description: 根据定时任务名称更新执行时间等内容
	 *
	 * @param entity
	 * @author Yangcl
	 * @date 2018年12月18日 下午5:15:38 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByJobName(JobInfo entity);
	

	/**
	 * @description:获取JobInfo列表，不分页 
	 *
	 * @author Yangcl
	 * @date 2018年12月19日 下午1:53:19 
	 * @version 1.0.0.1
	 */
	public List<JobInfo> findJobInfoList(JobInfoDto dto);

	/**
	 * @description: 定时任务列表页数据
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月20日 下午6:13:40 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoList(JobInfoDto dto, HttpServletRequest request);

	/**
	 * @description: 【添加/修改】定时任务-任务组下拉框列表数据-不分页
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月21日 下午11:53:15 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupList(JobGroupDto dto, HttpServletRequest request);


	/**
	 * @description: 添加定时任务
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:01:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnJobInfoAdd(JobInfo entity);

	/**
	 * @description: 编辑定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午1:58:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnJobInfoEdit(JobInfo entity);

	/**
	 * @description: 定时任务详情|根据jobName获取
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月24日 下午5:26:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoDetail(JobInfoDto dto);


	/**
	 * @description: 删除定时任务
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月25日 下午4:21:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoDelete(JobInfoDto dto);


	/**
	 * @description: 暂停一个定时任务 或 全部暂停
	 *
	 * @param dto.jobName
	 * @param dto.pause 定时任务是否暂停 0否|1是
	 * @param dto.pauseType one 暂停一个定时任务  all 暂停所有定时任务
	 * @author Yangcl
	 * @date 2018年12月25日 下午5:14:14 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoPause(JobInfoDto dto);


	/**
	 * @description: 定时任务分组列表页信息
	 *
	 * @param dto.groupName
	 * @param dto.ip
	 * @author Yangcl
	 * @date 2018年12月27日 上午11:24:35 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupPageList(JobGroupDto dto, HttpServletRequest request);

	/**
	 * @description: 添加定时任务分组
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupAdd(JobGroup entity);

	/**
	 * @description: 修改定时任务分组
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupEdit(JobGroup entity);

	/**
	 * @description: 定时任务分组详情
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午2:49:19 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupDetail(JobGroupDto dto);


	/**
	 * @description: 删除定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午3:29:36 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupDelete(JobGroupDto dto);

	/**
	 * @description: 定时任务日志列表页信息
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月29日 下午5:30:48 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobLogList(JobExecLogDto dto, HttpServletRequest request);

	/**
	 * @description:根据id获取定时任务日志详情
	 *
	 * @param dto.id
	 * @author Yangcl
	 * @date 2019年1月2日 下午3:42:19 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobLogDetail(JobExecLogDto dto);

	/**
	 * @description: 主动触发定时任务
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2019年9月26日 下午10:22:54 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoExec(JobInfoDto dto);
	
	
}
































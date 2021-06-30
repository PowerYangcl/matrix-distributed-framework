package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.request.AddJobGroupRequest;
import com.matrix.pojo.request.AddJobInfoRequest;
import com.matrix.pojo.request.DeleteJobInfoRequest;
import com.matrix.pojo.request.FindAjaxJobGroupListRequest;
import com.matrix.pojo.request.FindAjaxJobInfoListRequest;
import com.matrix.pojo.request.FindAjaxJobInfoRequest;
import com.matrix.pojo.request.FindJobGroupRequest;
import com.matrix.pojo.request.UpdateJobGroupRequest;
import com.matrix.pojo.request.UpdateJobInfoPauseRequest;
import com.matrix.pojo.request.UpdateJobInfoRequest;
import com.matrix.pojo.view.JobGroupView;
import com.matrix.pojo.view.JobInfoView;

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
	 * @author Yangcl
	 * @date 2018年12月20日 下午6:13:40 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<JobInfoView>> ajaxJobInfoList(FindAjaxJobInfoListRequest param, HttpServletRequest request);

	/**
	 * @description: 【添加/修改】定时任务-任务组下拉框列表数据-不分页
	 *
	 * @author Yangcl
	 * @date 2018年12月21日 下午11:53:15 
	 * @version 1.0.0.1
	 */
	public Result<List<JobGroup>> ajaxJobGroupList(FindAjaxJobGroupListRequest param, HttpServletRequest request);


	/**
	 * @description: 添加定时任务
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:01:51 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnAddJobInfo(AddJobInfoRequest param);

	/**
	 * @description: 编辑定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午1:58:51 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobInfoEdit(UpdateJobInfoRequest param);

	/**
	 * @description: 定时任务详情|根据jobName获取
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月24日 下午5:26:51 
	 * @version 1.0.0.1
	 */
	public Result<JSONObject> ajaxJobInfoDetail(FindAjaxJobInfoRequest param);


	/**
	 * @description: 删除定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月25日 下午4:21:07 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobInfoDelete(DeleteJobInfoRequest param);


	/**
	 * @description: 暂停一个定时任务 或 全部暂停
	 *
	 * @author Yangcl
	 * @date 2018年12月25日 下午5:14:14 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxJobInfoPause(UpdateJobInfoPauseRequest param);


	/**
	 * @description: 定时任务分组列表页信息
	 *
	 * @param param.groupName
	 * @param param.ip
	 * @author Yangcl
	 * @date 2018年12月27日 上午11:24:35 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<JobGroupView>> ajaxJobGroupPageList(FindAjaxJobGroupListRequest param , HttpServletRequest request);

	/**
	 * @description: 添加定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobGroupAdd(AddJobGroupRequest param);

	/**
	 * @description: 修改定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobGroupEdit(UpdateJobGroupRequest param);

	/**
	 * @deprecated
	 * @description: 定时任务分组详情|layui在编辑弹窗时不再需要查询详情，故此接口没有使用
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午2:49:19 
	 * @version 1.0.0.1
	 */
	public Result<JobGroup> ajaxJobGroupDetail(FindJobGroupRequest param);


	/**
	 * @description: 删除定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午3:29:36 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnJobGroupDelete(JobGroupDto dto);

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
































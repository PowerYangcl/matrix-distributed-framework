package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IJobService;

/**
 * @description: 定时任务统一封装与处理 
 *
 * @author Yangcl
 * @date 2017年11月2日 下午4:52:31 
 * @version 1.0.0
 */
@Controller
@RequestMapping("quartz")
public class QuartzCenterController extends BaseController{
	private static Logger logger=Logger.getLogger(QuartzCenterController.class);
	
	@Autowired
	private IJobService jobService;
	
	/**
	 * @description: 定时任务列表页
	 *
	 * @author Yangcl
	 * @date 2018年12月20日 下午5:31:49 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_quartz_job_info_list")  
	public String pageQuartzJobInfo(HttpSession session){ 
		super.userBehavior(session, logger, "page_quartz_job_info_list", "定时任务列表页");
		return "views/quartz/info/job-info-list";  
	}
	
	/**
	 * @description: 定时任务列表页数据
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月20日 下午6:13:40 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobInfoList(JobInfoDto dto , HttpSession session, HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_job_info_list", "定时任务列表页数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobInfoList(dto , request);
	}
	
	/**
	 * @description: 【添加/修改】定时任务-任务组下拉框列表数据-不分页
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月21日 下午11:53:15 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupList(JobGroupDto dto , HttpSession session, HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_job_group_list", "定时任务列表页数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupList(dto , request);
	}
	
	
	/**
	 * @description: 添加定时任务
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:01:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_job_info_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnJobInfoAdd(JobInfo entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_job_info_add", "添加定时任务");
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxBtnJobInfoAdd(entity);
	}
	
	/**
	 * @description: 编辑定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午1:58:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_job_info_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnJobInfoEdit(JobInfo entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_job_info_edit", "编辑定时任务");
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxBtnJobInfoEdit(entity);
	}
	
	/**
	 * @description: 主动触发定时任务
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2019年9月26日 下午10:22:54 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_info_exec", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobInfoExec(JobInfoDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_info_exec", "主动触发定时任务");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobInfoExec(dto);
	}
	
	/**
	 * @description: 定时任务详情|根据jobName获取
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月24日 下午5:26:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_info_detail", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobInfoDetail(JobInfoDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_info_detail", "定时任务详情");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobInfoDetail(dto);
	}
	
	/**
	 * @description: 删除定时任务
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月25日 下午4:21:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_job_info_delete", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnJobInfoDelete(JobInfoDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_job_info_delete", "删除定时任务");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxBtnJobInfoDelete(dto);
	}
	
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
	@RequestMapping(value = "ajax_job_info_pause", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobInfoPause(JobInfoDto dto , HttpSession session){
		if(dto.getPauseType().equals("one")) {
			super.userBehavior(session, logger, "ajax_job_info_pause", "暂停一个定时任务");
		}else {
			super.userBehavior(session, logger, "ajax_job_info_pause", "暂停全部定时任务");
		}
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobInfoPause(dto);
	}
	
	
	/**
	 * @description: 定时任务分组列表页
	 *
	 * @author Yangcl
	 * @date 2018年12月26日 下午4:45:25 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_quartz_job_group_list")  
	public String pageQuartzJobGroup(HttpSession session){ 
		super.userBehavior(session, logger, "page_quartz_job_group_list", "定时任务分组列表页");
		return "jsp/quartz/group/job-group";  
	}
	
	
	
	/**
	 * @description: 定时任务分组列表页信息
	 *
	 * @param dto.groupName
	 * @param dto.ip
	 * @author Yangcl
	 * @date 2018年12月27日 上午11:24:35 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_page_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupPageList(JobGroupDto dto , HttpSession session, HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_job_group_page_list", "定时任务分组列表页信息");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupPageList(dto , request);
	}
	
	
	/**
	 * @description: 添加定时任务分组
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupAdd(JobGroup entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_group_add", "添加定时任务分组");
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupAdd(entity);
	}
	
	/**
	 * @description: 定时任务分组详情
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午2:49:19 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_detail", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupDetail(JobGroupDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_group_detail", "定时任务分组详情");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupDetail(dto);
	}
	
	/**
	 * @description: 修改定时任务分组
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupEdit(JobGroup entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_group_edit", "修改定时任务分组");
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupEdit(entity);
	}
	
	/**
	 * @description: 删除定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午3:29:36 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_group_delete", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobGroupDelete(JobGroupDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_group_delete", "删除定时任务分组");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobGroupDelete(dto);
	}
	
	
	
	
	/**
	 * @description: 定时任务执行日志记录列表页
	 *
	 * @author Yangcl
	 * @date 2018年12月29日 下午4:53:18 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_quartz_job_log_list")  
	public String pageQuartzJobLog(HttpSession session , ModelMap map , HttpServletRequest request){ 
		super.userBehavior(session, logger, "page_quartz_job_log_list", "定时任务执行日志记录列表页");
		map.put("jobName", request.getParameter("jobName"));
		return "jsp/quartz/log/job-log";  
	}
	
	/**
	 * @description: 定时任务日志列表页信息
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月29日 下午5:30:48 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_log_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobLogList(JobExecLogDto dto , HttpSession session, HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_job_log_list", "定时任务日志列表页信息");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobLogList(dto , request);
	}
	
	/**
	 * @description:根据id获取定时任务日志详情
	 *
	 * @param dto.id
	 * @author Yangcl
	 * @date 2019年1月2日 下午3:42:19 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_job_log_detail", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxJobLogDetail(JobExecLogDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_job_log_detail", "根据id获取定时任务日志详情");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return jobService.ajaxJobLogDetail(dto);
	}
}
































































































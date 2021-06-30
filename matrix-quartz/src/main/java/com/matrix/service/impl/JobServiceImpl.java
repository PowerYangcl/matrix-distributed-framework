package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IJobExecLogMapper;
import com.matrix.dao.IJobGroupMapper;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.dto.PowerCacheDto;
import com.matrix.pojo.entity.JobExecLog;
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
import com.matrix.pojo.view.JobExecLogView;
import com.matrix.pojo.view.JobGroupView;
import com.matrix.pojo.view.JobInfoView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IJobService;
import com.matrix.service.IMatrixRouteService;
import com.matrix.util.UuidUtil;
import com.matrix.validate.StringValidate;


@Service("jobService")
public class JobServiceImpl extends BaseClass implements IJobService {     
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IJobInfoMapper jobInfoMapper;
	@Resource
	private IJobGroupMapper jobGroupMapper;
	@Resource
	private IJobExecLogMapper jobExecLogMapper;
	@Resource
	private IMatrixRouteService matrixRouteService;
	
	/**
	 * @description: 根据定时任务名称更新执行时间等内容
	 *			TODO 是否会出问题目前暂未发现 - 2021-06-09 - Yangcl
	 *
	 * @author Yangcl
	 * @date 2018年12月18日 下午5:15:38 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByJobName(JobInfo entity) {
		launch.loadDictCache(DCacheEnum.SysJob , "").del(entity.getJobName());     // 小于10分钟间隔的任务会出现问题 - 李玟霆
		return jobInfoMapper.updateSelectiveByJobName(entity); 
	}


	/**
	 * @description:获取JobInfo列表，不分页 
	 *
	 * @author Yangcl
	 * @date 2018年12月19日 下午1:53:19 
	 * @version 1.0.0.1
	 */
	public List<JobInfo> findJobInfoList(JobInfoDto dto) {
		return jobInfoMapper.findListByDto(dto);
	}

	
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////后台页面配置//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 定时任务列表页数据
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月20日 下午6:13:40 
	 * @version 1.0.0.1
	 */	
	public Result<PageInfo<JobInfoView>> ajaxJobInfoList(FindAjaxJobInfoListRequest param , HttpServletRequest request) {
    	int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			JobInfoDto dto = param.buildAjaxJobInfoList();
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = dto.getStartIndex();
				pageSize = dto.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<JobInfoView> list = jobInfoMapper.pageListByDto(dto);
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<JobInfoView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
    }

	/**
	 * @description: 【添加/修改】定时任务-任务组下拉框列表数据-不分页
	 *
	 * @author Yangcl
	 * @date 2018年12月21日 下午11:53:15 
	 * @version 1.0.0.1
	 */
	public Result<List<JobGroup>> ajaxJobGroupList(FindAjaxJobGroupListRequest param, HttpServletRequest request) {
		List<JobGroup> list = jobGroupMapper.findListByDto(param.buildAjaxJobGroupList());
		if(list != null && list.size() != 0) {
			return Result.SUCCESS(list);
		}
		// 200010014=没有找到可用的定时任务组数据
		return Result.ERROR(this.getInfo(200010014), ResultCode.RESULT_NULL);
	}

	/**
	 * @description: 添加定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:01:51 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnAddJobInfo(AddJobInfoRequest param) {
		Result<?> validate = param.validateAjaxBtnAddJobInfo();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobInfo e = param.buildAjaxBtnAddJobInfo();
			Integer flag = jobInfoMapper.insertSelective(e);
			if(flag == 1) {	// 200010017=定时任务添加成功
				return Result.SUCCESS(this.getInfo(200010017));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010017), ResultCode.ERROR_INSERT);		// 200010017=定时任务添加失败
	}

	/**
	 * @description: 编辑定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午1:58:51 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobInfoEdit(UpdateJobInfoRequest param) {
		Result<?> validate = param.validateAjaxBtnJobInfoEdit();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobInfo e = param.buildAjaxBtnJobInfoEdit();
			Integer flag = jobInfoMapper.updateSelective(e);
			if(flag == 1) {
				JobInfo find = jobInfoMapper.find(e.getId());
				launch.loadDictCache(DCacheEnum.SysJob , "").del(find.getJobName());   // 保证定时任务名称永远不变
				return Result.SUCCESS(this.getInfo(200010027));		// 200010027=定时任务修改成功
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010028), ResultCode.ERROR_UPDATE);		// 200010028=定时任务修改失败
	}


	/**
	 * @description: 定时任务详情|根据jobName获取
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午5:26:51 
	 * @version 1.0.0.1
	 */
	public Result<JSONObject> ajaxJobInfoDetail(FindAjaxJobInfoRequest param) {
		Result<JSONObject> validate = param.validateAjaxJobInfoDetail();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobInfoDto dto = param.buildAjaxJobInfoDetail();
			String value = launch.loadDictCache(DCacheEnum.SysJob , "SysJobInit").get(dto.getJobName()); 
			if(StringUtils.isNotBlank(value)) {		
				return Result.SUCCESS(this.getInfo(200010030), JSONObject.parseObject(value));	// 200010030=定时任务详情获取成功
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010031), ResultCode.NOT_FOUND);		// 200010031=定时任务详情获取失败
	}


	/**
	 * @description: 删除定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月25日 下午4:21:07 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobInfoDelete(DeleteJobInfoRequest param) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobInfo e = param.build();
			Integer flag = jobInfoMapper.updateSelectiveByJobName(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.SysJob , "").del(param.getJobName()); 
				return Result.SUCCESS(this.getInfo(200010034));   // 200010034=定时任务删除成功
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010032), ResultCode.ERROR_DELETE);		// 200010032=定时任务删除失败
	}


	/**
	 * @description: 暂停一个定时任务 或 全部暂停
	 *
	 * @author Yangcl
	 * @date 2018年12月25日 下午5:14:14 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxJobInfoPause(UpdateJobInfoPauseRequest param) {
		Result<?> validate = param.validateAjaxJobInfoPause();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		String msg = "";
		try {
			if(param.getPauseType().equals("one")) {
				JobInfo e = param.buildAjaxJobInfoPauseOne();
				Integer flag = jobInfoMapper.updateSelectiveByJobName(e);
				if(flag == 1) {
					launch.loadDictCache(DCacheEnum.SysJob , "").del(param.getJobName()); 
					msg = this.getInfo(200010038);  // 200010038=定时任务恢复成功
					if(param.getPause() == 1) {
						msg = this.getInfo(200010035);  // 200010035=定时任务暂停成功
					}
					return Result.SUCCESS(msg);
				} 
				
				msg = this.getInfo(200010039);  // 200010039=定时任务恢复失败
				if(param.getPause() == 1) {
					msg = this.getInfo(200010036);  // 200010036=定时任务暂停失败
				}
				return Result.ERROR(msg, ResultCode.ERROR_UPDATE);
			}
			
			if(param.getPauseType().equals("all")) {
				JobInfo e = param.buildAjaxJobInfoPauseAll();
				Integer flag = jobInfoMapper.pauseAll(e); // 开始批量更新数据库中的所有状态 
				if(flag != 0) {
					launch.loadDictCache(DCacheEnum.SysJob , null).batchDeleteByPrefix("");
					msg = this.getInfo(200010038);  // 200010038=定时任务恢复成功
					if(param.getPause() == 1) {
						msg =  this.getInfo(200010035);  // 200010035=定时任务暂停成功
					}
					return Result.SUCCESS(msg);
				} 
				
				msg = this.getInfo(200010039);  	// 200010039=定时任务恢复失败
				if(param.getPause() == 1) {
					msg = this.getInfo(200010036);  	// 200010036=定时任务暂停失败
				}
				return Result.ERROR(msg, ResultCode.ERROR_UPDATE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(msg, ResultCode.ERROR_UPDATE);
	}


	/**
	 * @description: 定时任务分组列表页信息
	 *
	 * @param param.groupName
	 * @param param.ip
	 * @author Yangcl
	 * @date 2018年12月27日 上午11:24:35 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<JobGroupView>> ajaxJobGroupPageList(FindAjaxJobGroupListRequest param , HttpServletRequest request) {
    	int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			JobGroupDto dto = param.buildAjaxJobGroupPageList();
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = dto.getStartIndex();
				pageSize = dto.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<JobGroupView> list = jobGroupMapper.pageListByDto(dto);
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<JobGroupView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
    }
	

	/**
	 * @description: 添加定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnJobGroupAdd(AddJobGroupRequest param) {
		Result<?> validate = param.validateAjaxBtnJobGroupAdd();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobGroup e = param.buildAjaxBtnJobGroupAdd();
			Integer flag = jobGroupMapper.insertSelective(e);
			if(flag == 1) {
				return Result.SUCCESS(this.getInfo(200010040));  //  // 200010040=定时任务分组添加成功
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010041), ResultCode.ERROR_INSERT);		// 200010041=定时任务分组添加失败, 请重试!
	}
	
	/**
	 * @deprecated
	 * @description: 定时任务分组详情|layui在编辑弹窗时不再需要查询详情，故此接口没有使用
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午2:49:19 
	 * @version 1.0.0.1
	 */
	public Result<JobGroup> ajaxJobGroupDetail(FindJobGroupRequest param) {
		Result<JobGroup> validate = param.validateAjaxJobGroupDetail();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			String value = launch.loadDictCache(DCacheEnum.SysJobGroup , "SysJobGroupInit").get(String.valueOf(param.getId())); 
			if(StringUtils.isNotBlank(value)) {
				JobGroup entity = JSONObject.parseObject(value, JobGroup.class);
				if(entity != null) {
					return Result.SUCCESS(this.getInfo(200010049), entity);		// 200010049=定时任务分组详情获取成功
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010050), ResultCode.NOT_FOUND);	// 200010050=定时任务分组详情获取失败, 请重试!
	}

	/**
	 * @description: 修改定时任务分组|修改定时任务分组的IP地址可以控制定时任务在定时任务
	 * 		服务器集群中的执行强度(个数) 非并发类的定时任务会显著受到影响
	 *
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> ajaxBtnJobGroupEdit(UpdateJobGroupRequest param) {
		Result<?> validate = param.validateAjaxBtnJobGroupEdit();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			JobGroup find = jobGroupMapper.find(param.getId());
			JobGroup entity = param.buildAjaxBtnJobGroupEdit();
			Integer flag = jobGroupMapper.updateSelective(entity);
			if(flag == 1) {
				if(!find.getIp().equals(param.getIp())) {  // 批量删除所有定时任务缓存信息 同时 删除当前定时任务分组缓存信息|广谱删除
					launch.loadDictCache(DCacheEnum.SysJobGroup , null).batchDeleteByPrefix("");
					launch.loadDictCache(DCacheEnum.SysJob , null).batchDeleteByPrefix("");
				}
				return Result.SUCCESS(this.getInfo(200010042));  // 200010042=定时任务分组修改成功
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(200010043), ResultCode.ERROR_UPDATE);		// 200010043=定时任务分组修改失败, 请重试!
	}


	/**
	 * @description: 删除定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午3:29:36 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnJobGroupDelete(JobGroupDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010051));  // 200010051=定时任务分组删除失败, 请重试!
			return result;
		}
		
		JobInfoDto jobInfoDto = new JobInfoDto();
		jobInfoDto.setRunGroupId(dto.getId());
		List<JobInfo> correlation = jobInfoMapper.findListByDto(jobInfoDto);
		if(correlation != null && correlation.size() != 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010047 , correlation.size()));  // 200010047=定时任务分组删除失败, 已有{0}个定时任务与该分组关联，请解除
			return result;
		}
		
		JobGroup e = new JobGroup();
		e.setId(dto.getId());
		e.setDeleteFlag(0);
		e.setUpdateTime(new Date());
		e.setUpdateUserId(dto.getUserCache().getId());
		e.setUpdateUserName(dto.getUserCache().getUserName());
		Integer flag = jobGroupMapper.updateSelective(e);
		if(flag == 1) {
			launch.loadDictCache(DCacheEnum.SysJobGroup , null).del(e.getId().toString());
			result.put("status", "success");
			result.put("msg", this.getInfo(200010052));  // 200010052=定时任务分组删除成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010051));  // 200010051=定时任务分组删除失败, 请重试!
		}
		return result;
	}


	/**
	 * @description: 定时任务日志列表页信息
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年12月29日 下午5:30:48 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobLogList(JobExecLogDto dto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
    	int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
    	if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
    		pageNum = dto.getStartIndex();
    		pageSize = dto.getPageSize();
    	}else{
    		pageNum = Integer.parseInt(request.getParameter("pageNum")); 
    		pageSize = Integer.parseInt(request.getParameter("pageSize")); 
    	}
 
    	try {
    		PageHelper.startPage(pageNum , pageSize);
    		List<JobExecLogView> list = jobExecLogMapper.pageListByDto(dto);
    		result.put("status", "success");
    		if (list != null && list.size() > 0) {
    			result.put("code" , RpcResultCode.SUCCESS);
    			result.put("msg", this.getInfo(100010114));  // 100010114=分页数据返回成功!
    		} else {
    			result.put("code" , RpcResultCode.RESULT_NULL);
    			result.put("msg", this.getInfo(100010115));  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
    		}
    		PageInfo<JobExecLogView> pageList = new PageInfo<JobExecLogView>(list);
    		result.put("data", pageList);
    		return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(100010116));  // 100010116=分页数据返回失败，服务器异常!
			return result;
		}
	}


	/**
	 * @description:根据id获取定时任务日志详情
	 *
	 * @param dto.id
	 * @author Yangcl
	 * @date 2019年1月2日 下午3:42:19 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobLogDetail(JobExecLogDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010055));  // 200010055=定时任务日志获取失败!日志主键id为空, 无法查询
			return result;
		}	
		JobExecLog find = jobExecLogMapper.find(dto.getId());
		if(find == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010054));  // 200010054=定时任务日志获取失败!请重试即可
			return result;
		}
		result.put("status", "success");
		result.put("msg", this.getInfo(200010053));  // 200010053=定时任务日志获取成功!
		result.put("e", find);
		return result;
	}


	/**
	 * @description: 主动触发定时任务
	 *
	 * @param dto
	 * @param session
	 * @author Yangcl
	 * @date 2019年9月26日 下午10:22:54 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoExec(JobInfoDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010056));  // 200010056=定时任务主动触发失败!主键id为空
			return result;
		}
		try {
			String[] arr  = dto.getIps().split(",");  
			String port = "28110";  // 28110端口为Job项目默认dubbo端口号
			if(StringUtils.isNotBlank(this.getConfig("matrix-quartz.job_port"))) {
				port = this.getConfig("matrix-quartz.job_port");
			}
			List<String> list = new ArrayList<String>(arr.length);
			for(String ip : arr) {
				list.add(ip + ":" +port);
			}
			String dubboAddr = "";
			for(String addr : list) {
				dubboAddr = dubboAddr + addr + ",";
			}
			dubboAddr = dubboAddr.substring(0, dubboAddr.length() - 1);
			
			PowerCacheDto pcd = new PowerCacheDto();
			pcd.setDubboAddr(dubboAddr);
			pcd.setKey("guard_job_exec");
			pcd.setValue(JSONObject.toJSONString(dto));
			return matrixRouteService.ajaxRouteExecute(pcd);
		} catch (Exception e) {
			e.printStackTrace(); 
			result.put("status", "error");
			result.put("msg", this.getInfo(200010058));  // 200010058=定时任务主动触发失败
			return result;
		}
	}


	
}




































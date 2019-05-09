package com.matrix.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseClass;
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
import com.matrix.pojo.entity.JobExecLog;
import com.matrix.pojo.entity.JobGroup;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.JobExecLogView;
import com.matrix.pojo.view.JobGroupView;
import com.matrix.pojo.view.JobInfoView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IJobService;
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
	
	/**
	 * @description: 根据定时任务名称更新执行时间等内容
	 *
	 * @param entity
	 * @author Yangcl
	 * @date 2018年12月18日 下午5:15:38 
	 * @version 1.0.0.1
	 */
	public Integer updateSelectiveByJobName(JobInfo entity) {
		launch.loadDictCache(DCacheEnum.SysJob , "").del(entity.getJobName());  
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
	public JSONObject ajaxJobInfoList(JobInfoDto dto , HttpServletRequest request) {
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
 
		PageHelper.startPage(pageNum , pageSize);
		List<JobInfoView> list = jobInfoMapper.pageListByDto(dto);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		PageInfo<JobInfoView> pageList = new PageInfo<JobInfoView>(list);
		result.put("data", pageList);
		return result;
    }


	/**
	 * @description: 【添加/修改】定时任务-任务组下拉框列表数据-不分页
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月21日 下午11:53:15 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupList(JobGroupDto dto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		List<JobGroup> list = jobGroupMapper.findListByDto(dto);
		if(list != null && list.size() != 0) {
			result.put("status", "success");
			result.put("list", list);
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010014));  // 200010014=没有找到可用的定时任务组数据
		}
		return result;
	}


	/**
	 * @description: 添加定时任务
	 *
	 * @param e 
	 * @author Yangcl
	 * @date 2018年12月22日 下午3:01:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoAdd(JobInfo e) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getJobTitle(),e.getJobTriger() ,e.getJobClass() , e.getRemark())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010015));  // 200010015=定时任务添加弹窗中的字段均为必填项，请核查
			return result;
		}
		if(e.getRunGroupId() == null || e.getExpireTime() == null || e.getTimeOut() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010015));  // 200010015=定时任务添加弹窗中的字段均为必填项，请核查
			return result;
		}
		if(!StringValidate.isNumeric(e.getExpireTime().toString()) || !StringValidate.isNumeric(e.getTimeOut().toString())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010016));  // 200010016=锁有效时间和超时时间为正数
			return result;
		}
		
		List<JobInfo> ishas = jobInfoMapper.findList(e); // e.getJobTitle() e.getJobClass()
		if(ishas != null && ishas.size() != 0) {
			for(JobInfo info : ishas) {
				result.put("status", "error");
				if(info.getJobTitle().equals(e.getJobTitle())) {
					result.put("msg", this.getInfo(200010019));  // 200010019=定时任务添加失败，定时任务标题在系统中已经存在
				}
				if(info.getJobClass().equals(e.getJobClass())) {
					result.put("msg", this.getInfo(200010021));  // 200010021=定时任务添加失败，【定时任务执行类路径】在系统中已经存在
				}
				return result;
			}
		}
		
		// 开始添加数据 
		McUserInfoView userCache = e.getUserCache();
		e.setJobName(UuidUtil.upperCaseUuid());
		e.setLockKey(e.getJobName());
		e.setPause(0);  // 定时任务默认暂停
		e.setCreateTime(new Date());
		e.setCreateUserName(userCache.getUserName());
		e.setCreateUserId(userCache.getId());
		e.setUpdateTime(new Date());
		e.setUpdateUserName(userCache.getUserName()); 
		e.setUpdateUserId(userCache.getId());
		Integer flag = jobInfoMapper.insertSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(200010017));  // 200010017=定时任务添加成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010018));  // 200010017=定时任务添加失败
		}
		
		return result;
	}


	/**
	 * @description: 编辑定时任务
	 *
	 * @author Yangcl
	 * @date 2018年12月24日 下午1:58:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoEdit(JobInfo e) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getJobTitle(),e.getJobTriger() ,e.getJobClass() , e.getRemark())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010015));  // 200010015=定时任务添加弹窗中的字段均为必填项，请核查
			return result;
		}
		if(e.getRunGroupId() == null || e.getExpireTime() == null || e.getTimeOut() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010015));  // 200010015=定时任务添加弹窗中的字段均为必填项，请核查
			return result;
		}
		if(!StringValidate.isNumeric(e.getExpireTime().toString()) || !StringValidate.isNumeric(e.getTimeOut().toString())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010016));  // 200010016=锁有效时间和超时时间为正数
			return result;
		}
		if(e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010024));  // 200010024=信息更新失败，主键丢失
			return result;
		}
		
		JobInfo find = jobInfoMapper.find(e.getId());
		if(find == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010022));  // 200010022=字段排重验证出现异常，请重试即可
			return result;
		}
		if(!find.getJobTitle().equals(e.getJobTitle())) {
			JobInfo e_ = new JobInfo();
			e_.setJobTitle(e.getJobTitle());
			List<JobInfo> ishas = jobInfoMapper.findList(e_); // e.getJobTitle()
			if(ishas != null && ishas.size() != 0) {
				result.put("status", "error");
				result.put("msg", this.getInfo(200010025));  // 200010025=定时任务修改失败，【定时任务标题】在系统中已经存在
				return result;
			}
		}
		if(!find.getJobClass().equals(e.getJobClass())) {
			JobInfo e_ = new JobInfo();
			e_.setJobClass(e.getJobClass());
			List<JobInfo> ishas = jobInfoMapper.findList(e_); // e.getJobClass()
			if(ishas != null && ishas.size() != 0) {
				result.put("status", "error");
				result.put("msg", this.getInfo(200010026));  // 200010026=定时任务修改失败，【定时任务执行类路径】在系统中已经存在
				return result;
			}
		}
		
		launch.loadDictCache(DCacheEnum.SysJob , "").del(find.getJobName());   // 保证定时任务名称永远不变
		e.setJobName(null);
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer flag = jobInfoMapper.updateSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(200010027));  // 200010027=定时任务修改成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010028));  // 200010028=定时任务修改失败
		}
		return result;
	}


	/**
	 * @description: 定时任务详情|根据jobName获取
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月24日 下午5:26:51 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoDetail(JobInfoDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getJobName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010029));  // 200010029=定时任务详情获取失败, job-name为空, 请重试
			return result;
		}
		launch.loadDictCache(DCacheEnum.SysJob , "").del(dto.getJobName()); 
		String value = launch.loadDictCache(DCacheEnum.SysJob , "InitSysJob").get(dto.getJobName()); 
		if(StringUtils.isNotBlank(value)) {
			result = JSONObject.parseObject(value);
			result.put("status", "success");
			result.put("msg", this.getInfo(200010030));  // 200010030=定时任务详情获取成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010031));  // 200010031=定时任务详情获取失败
		}
		
		return result;
	}


	/**
	 * @description: 删除定时任务
	 *
	 * @param dto 
	 * @author Yangcl
	 * @date 2018年12月25日 下午4:21:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobInfoDelete(JobInfoDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getJobName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010033));  // 200010033=定时任务删除失败, job-name为空, 请重试
			return result;
		}
		
		JobInfo e = new JobInfo();
		e.setJobName(dto.getJobName());
		e.setDeleteFlag(0);
		e.setUpdateTime(new Date());
		e.setUpdateUserId(dto.getUserCache().getId());
		e.setUpdateUserName(dto.getUserCache().getUserName());
		Integer flag = jobInfoMapper.updateSelectiveByJobName(e);
		if(flag == 1) {
			launch.loadDictCache(DCacheEnum.SysJob , "").del(dto.getJobName()); 
			result.put("status", "success");
			result.put("msg", this.getInfo(200010034));  // 200010034=定时任务删除成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010032));  // 200010032=定时任务删除失败
		}
		return result;
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
	public JSONObject ajaxJobInfoPause(JobInfoDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getPauseType().equals("one") && StringUtils.isBlank(dto.getJobName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010037));  // 200010037=定时任务暂停失败, job-name为空, 请重试
			return result;
		}
		if(dto.getPause() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010036));  // 200010036=定时任务暂停失败
			return result;
		}
		
		if(dto.getPauseType().equals("one")) {
			JobInfo e = new JobInfo();
			e.setPause(dto.getPause());
			e.setJobName(dto.getJobName());
			e.setUpdateTime(new Date());
			e.setUpdateUserId(dto.getUserCache().getId());
			e.setUpdateUserName(dto.getUserCache().getUserName());
			Integer flag = jobInfoMapper.updateSelectiveByJobName(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.SysJob , "").del(dto.getJobName()); 
				result.put("status", "success");
				if(dto.getPause() == 1) {
					result.put("msg", this.getInfo(200010035));  // 200010035=定时任务暂停成功
				}else {
					result.put("msg", this.getInfo(200010038));  // 200010038=定时任务恢复成功
				}
			}else {
				result.put("status", "error");
				if(dto.getPause() == 1) {
					result.put("msg", this.getInfo(200010036));  // 200010036=定时任务暂停失败
				}else {
					result.put("msg", this.getInfo(200010039));  // 200010039=定时任务恢复失败
				}
			}
			return result;
		}
		
		if(dto.getPauseType().equals("all")) {
			JobInfo e = new JobInfo();
			e.setPause(dto.getPause());
			e.setUpdateTime(new Date());
			e.setUpdateUserId(dto.getUserCache().getId());
			e.setUpdateUserName(dto.getUserCache().getUserName());
			Integer flag = jobInfoMapper.pauseAll(e); // 开始批量更新数据库中的所有状态 
			if(flag != 0) {
				launch.loadDictCache(DCacheEnum.SysJob , null).batchDel("");
				result.put("status", "success");
				if(dto.getPause() == 1) {
					result.put("msg", this.getInfo(200010035));  // 200010035=定时任务暂停成功
				}else {
					result.put("msg", this.getInfo(200010038));  // 200010038=定时任务恢复成功
				}
				return result;
			}else {
				result.put("status", "error");
				if(dto.getPause() == 1) {
					result.put("msg", this.getInfo(200010036));  // 200010036=定时任务暂停失败
				}else {
					result.put("msg", this.getInfo(200010039));  // 200010039=定时任务恢复失败
				}
				return result;
			}
		}
		
		result.put("status", "error");
		result.put("msg", this.getInfo(200010036));  // 200010036=定时任务暂停失败
		return null;
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
	public JSONObject ajaxJobGroupPageList(JobGroupDto dto, HttpServletRequest request) {
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
 
		PageHelper.startPage(pageNum , pageSize);
		List<JobGroupView> list = jobGroupMapper.pageListByDto(dto);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		PageInfo<JobGroupView> pageList = new PageInfo<JobGroupView>(list);
		result.put("data", pageList);
		return result;
	}


	/**
	 * @description: 添加定时任务分组
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupAdd(JobGroup e) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getGroupName() , e.getIp())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010045));  // 200010045=定时任务分组必填字段验证失败!弹窗中的字段均为必填项，请核查!
			return result;
		}
		List<JobGroup> ishas = jobGroupMapper.findList(e);
		if(ishas != null && ishas.size() != 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010044));  // 200010044=定时任务分组添加失败，分组名称已经存在
			return result;
		}
		
		e.setCreateTime(new Date());
		e.setCreateUserName(e.getUserCache().getUserName());
		e.setCreateUserId(e.getUserCache().getId());
		e.setUpdateTime(new Date());
		e.setUpdateUserName(e.getUserCache().getUserName());
		e.setUpdateUserId(e.getUserCache().getId());
		Integer flag = jobGroupMapper.insertSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(200010040));  // 200010040=定时任务分组添加成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010041));  // 200010041=定时任务分组添加失败, 请重试!
		}
		return result;
	}
	
	/**
	 * @description: 定时任务分组详情
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午2:49:19 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupDetail(JobGroupDto dto) {
		JSONObject result = new JSONObject();
		if(dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010048));  // 200010048=定时任务分组详情获取失败, 主键丢失, 请重试!
			return result;
		}
		
		launch.loadDictCache(DCacheEnum.SysJobGroup , "").del(String.valueOf(dto.getId())); 
		String value = launch.loadDictCache(DCacheEnum.SysJobGroup , "InitSysJobGroup").get(String.valueOf(dto.getId())); 
		if(StringUtils.isNotBlank(value)) {
			result = JSONObject.parseObject(value);
			result.put("status", "success");
			result.put("msg", this.getInfo(200010049));  // 200010049=定时任务分组详情获取成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010050));  // 200010050=定时任务分组详情获取失败, 请重试!
		}
		return result;
	}

	/**
	 * @description: 修改定时任务分组|修改定时任务分组的IP地址可以控制定时任务在定时任务服务器集群中的执行强度(个数) 非并发类的定时任务会显著受到影响
	 *
	 * @param entity 
	 * @author Yangcl
	 * @date 2018年12月27日 下午3:20:21 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupEdit(JobGroup e) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getGroupName() , e.getIp())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010045));  // 200010045=定时任务分组必填字段验证失败!弹窗中的字段均为必填项，请核查!
			return result;
		}
		if(e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010024));  // 200010024=信息更新失败，主键丢失
			return result;
		}
		JobGroup find = jobGroupMapper.find(e.getId());
		if(find == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010022));  // 200010022=字段排重验证出现异常，请重试即可
			return result;
		}
		if(!find.getGroupName().equals(e.getGroupName())) {
			List<JobGroup> ishas = jobGroupMapper.findList(e);
			if(ishas != null && ishas.size() != 0) {
				result.put("status", "error");
				result.put("msg", this.getInfo(200010046));  // 200010046=定时任务分组修改失败, 分组名称已经存在
				return result;
			}
		}
		
		if(!find.getIp().equals(e.getIp())) {  // 批量删除所有定时任务缓存信息 同时 删除当前定时任务分组缓存信息|广谱删除
			launch.loadDictCache(DCacheEnum.SysJobGroup , null).batchDel("");
			launch.loadDictCache(DCacheEnum.SysJob , null).batchDel("");
		}
		
		e.setUpdateTime(new Date());
		e.setUpdateUserId(e.getUserCache().getId());
		e.setUpdateUserName(e.getUserCache().getUserName());
		Integer flag = jobGroupMapper.updateSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(200010042));  // 200010042=定时任务分组修改成功
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(200010043));  // 200010043=定时任务分组修改失败, 请重试!
		}
		return result;
	}


	/**
	 * @description: 删除定时任务分组
	 *
	 * @author Yangcl
	 * @date 2018年12月28日 下午3:29:36 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxJobGroupDelete(JobGroupDto dto) {
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
 
		PageHelper.startPage(pageNum , pageSize);
		List<JobExecLogView> list = jobExecLogMapper.pageListByDto(dto);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		PageInfo<JobExecLogView> pageList = new PageInfo<JobExecLogView>(list);
		result.put("data", pageList);
		return result;
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


	
}




































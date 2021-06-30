package com.matrix.pojo.request;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.util.UuidUtil;
import com.matrix.validate.StringValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddJobInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 5765724373285656877L;

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	private McUserInfoView userCache;
	
    private String jobTitle;
    private String jobClass;
    private String jobTriger;
    private Long runGroupId;
    private Integer expireTime;
    private Integer timeOut;
    private String jobList; 
    private Integer trigerType;
    private Integer logType;
    private String remark;
	
    public Result<?> validateAjaxBtnAddJobInfo() {
		if(StringUtils.isAnyBlank(jobTitle, jobClass ,jobTriger ,  remark)){  
			// 200010015=定时任务弹窗中的字段均为必填项，请核查
			return Result.ERROR(this.getInfo(200010015), ResultCode.MISSING_ARGUMENT);
		}
		if(runGroupId == null || expireTime == null || timeOut == null) {
			// 200010015=定时任务弹窗中的字段均为必填项，请核查
			return Result.ERROR(this.getInfo(200010015), ResultCode.MISSING_ARGUMENT);
		}
		if(!StringValidate.isNumeric(expireTime.toString()) || !StringValidate.isNumeric(timeOut.toString())) {
			// 200010016=锁有效时间和超时时间为正数
			return Result.ERROR(this.getInfo(200010016), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		
		JobInfo e = new JobInfo();
		e.setJobTitle(jobTitle);
		e.setJobClass(jobClass);
		List<JobInfo> ishas = jobInfoMapper.findList(e); // e.getJobTitle() e.getJobClass()
		if(ishas != null && ishas.size() != 0) {
			for(JobInfo info : ishas) {
				if(info.getJobTitle().equals(jobTitle)) {		// 200010019=定时任务添加失败，定时任务标题在系统中已经存在
					return Result.ERROR(this.getInfo(200010019), ResultCode.ALREADY_EXISTS);
				}
				if(info.getJobClass().equals(jobClass)) {		// 200010021=定时任务添加失败，【定时任务执行类路径】在系统中已经存在
					return Result.ERROR(this.getInfo(200010021), ResultCode.ALREADY_EXISTS);
				}
			}
		}
		return Result.SUCCESS();
	}
    
	public JobInfo buildAjaxBtnAddJobInfo() {
		JobInfo e = new JobInfo();
		e.setJobName(UuidUtil.upperCaseUuid());
		e.setJobTitle(jobTitle);
		e.setJobClass(jobClass);
		e.setJobTriger(jobTriger);
		e.setRunGroupId(runGroupId);
		e.setPause(0);  				// 定时任务默认暂停
		e.setLockKey(e.getJobName());
		e.setExpireTime(expireTime);
		e.setTimeOut(timeOut);
		e.setJobList(jobList);
		e.setTrigerType(trigerType);
		e.setLogType(logType);
		e.setRemark(remark);
		e.buildAddCommon(userCache);
		return e;
	}
}















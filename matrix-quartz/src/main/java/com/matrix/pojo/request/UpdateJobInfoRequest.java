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
import com.matrix.validate.StringValidate;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateJobInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 793906089044220261L;

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	private McUserInfoView userCache;
	
	private Long id;
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
    private Integer concurrentType;
	
    public Result<?> validateAjaxBtnJobInfoEdit() {
    	if(id == null){ 
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
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
		
		JobInfo find = jobInfoMapper.find(id);
		if(find == null) {	// 200010022=字段排重验证出现异常，请重试即可
			return Result.ERROR(this.getInfo(200010022), ResultCode.NOT_FOUND);
		}
		if(!find.getJobTitle().equals(jobTitle)) {
			JobInfo e = new JobInfo();
			e.setJobTitle(jobTitle);
			List<JobInfo> ishas = jobInfoMapper.findList(e);
			if(ishas != null && ishas.size() != 0) {
				// 200010025=定时任务修改失败，【定时任务标题】在系统中已经存在
				return Result.ERROR(this.getInfo(200010025), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
		}
		if(!find.getJobClass().equals(jobClass)) {
			JobInfo e = new JobInfo();
			e.setJobClass(jobClass);
			List<JobInfo> ishas = jobInfoMapper.findList(e);
			if(ishas != null && ishas.size() != 0) {
				// 200010026=定时任务修改失败，【定时任务执行类路径】在系统中已经存在
				return Result.ERROR(this.getInfo(200010026), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
		}
		return Result.SUCCESS();
	}
    
	public JobInfo buildAjaxBtnJobInfoEdit() {
		JobInfo e = new JobInfo();
		e.setId(id);
		e.setJobTitle(jobTitle);
		e.setJobClass(jobClass);
		e.setJobTriger(jobTriger);
		e.setRunGroupId(runGroupId);
		e.setExpireTime(expireTime);
		e.setTimeOut(timeOut);
		e.setJobList(jobList);
		e.setTrigerType(trigerType);
		e.setLogType(logType);
		e.setRemark(remark);
		e.setConcurrentType(concurrentType);
		e.buildUpdateCommon(userCache);
		return e;
	}
}















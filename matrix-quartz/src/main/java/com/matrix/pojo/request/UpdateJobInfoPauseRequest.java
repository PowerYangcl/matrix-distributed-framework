package com.matrix.pojo.request;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.entity.JobInfo;
import com.matrix.pojo.view.McUserInfoView;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateJobInfoPauseRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 8798500042319777424L;

	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	private McUserInfoView userCache;
	
	private Integer pause;			// 定时任务是否暂停 0否|1是
    private String jobName;
    private String pauseType;  // one 暂停一个定时任务  all 暂停所有定时任务
	
    public Result<?> validateAjaxJobInfoPause() {
    	if(StringUtils.isBlank("pauseType")) {	// 100020103=参数缺失：{0}
    		return Result.ERROR(this.getInfo(100020103, "pauseType"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(pause == null) {	// 100020103=参数缺失：{0}
    		return Result.ERROR(this.getInfo(100020103, "pause"), ResultCode.MISSING_ARGUMENT);
    	}
    	if(!pauseType.equals("one") && !pauseType.equals("all")) {
    		// 100020110=参数不匹配
    		return Result.ERROR(this.getInfo(100020110), ResultCode.MISMATCH_ARGUMENT);
    	}
    	if(pauseType.equals("one") && StringUtils.isBlank(jobName)) {	// 100020103=参数缺失：{0}
    		return Result.ERROR(this.getInfo(100020103, "jobName"), ResultCode.MISSING_ARGUMENT);
    	}
		return Result.SUCCESS();
	}
    
    public JobInfo buildAjaxJobInfoPauseOne() {
		JobInfo e = new JobInfo();
		e.setPause(pause);
		e.setJobName(jobName);
		e.buildUpdateCommon(userCache);
		return e;
	}
    
    public JobInfo buildAjaxJobInfoPauseAll() {
		JobInfo e = new JobInfo();
		e.setPause(pause);
		e.buildUpdateCommon(userCache);
		return e;
	}
}















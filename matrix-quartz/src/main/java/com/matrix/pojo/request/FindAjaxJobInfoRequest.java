package com.matrix.pojo.request;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindAjaxJobInfoRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 873293764288778619L;

	private McUserInfoView userCache;
	
    private String jobName; 
    
    public Result<JSONObject> validateAjaxJobInfoDetail(){
    	if(StringUtils.isBlank(jobName)) {
    		// 200010029=定时任务详情获取失败, job-name为空, 请重试
    		return Result.ERROR(this.getInfo(200010029), ResultCode.MISSING_ARGUMENT);
    	}
    	return Result.SUCCESS();
    }
	
	public JobInfoDto buildAjaxJobInfoDetail() {
		JobInfoDto dto = new JobInfoDto();
		dto.setJobName(jobName);
		return dto;
	}
}















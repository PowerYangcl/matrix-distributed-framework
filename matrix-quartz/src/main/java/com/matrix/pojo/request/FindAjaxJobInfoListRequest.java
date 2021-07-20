package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.dto.JobInfoDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindAjaxJobInfoListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 2512195098843251181L;

	private McUserInfoView userCache;
	
	private Long id;
	
    private String jobName;
    private String jobTitle;
    private String jobClass;
    private Integer trigerType;
    private Long runGroupId;
	
	public JobInfoDto buildAjaxJobInfoList() {
		JobInfoDto dto = new JobInfoDto();
		dto.setJobName(jobName);
		dto.setJobTitle(jobTitle);
		dto.setJobClass(jobClass);
		dto.setTrigerType(trigerType);
		dto.setRunGroupId(runGroupId);
		return dto;
	}
}















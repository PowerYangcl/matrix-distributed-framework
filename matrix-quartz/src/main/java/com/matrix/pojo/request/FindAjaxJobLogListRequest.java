package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.dto.JobExecLogDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindAjaxJobLogListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 6234025329986151317L;

	private McUserInfoView userCache;
	
    private String jobName;
    private String jobTitle;
    private String status;
    private String ip;
    private String runGroupName;
	
	public JobExecLogDto buildAjaxJobLogPageList() {
		JobExecLogDto dto = new JobExecLogDto();
		dto.setJobName(jobName);
		dto.setJobTitle(jobTitle);
		dto.setStatus(status);
		dto.setIp(ip);
		dto.setRunGroupName(runGroupName);
		return dto;
	}
}















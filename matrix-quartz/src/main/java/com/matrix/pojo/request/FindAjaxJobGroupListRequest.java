package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.base.BaseClass;
import com.matrix.pojo.dto.JobGroupDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class FindAjaxJobGroupListRequest extends BaseClass implements Serializable{

	private static final long serialVersionUID = 1077949835854301166L;

	private McUserInfoView userCache;
	
	private Long id;
	
    private String groupName;
    private String ip;
	
	public JobGroupDto buildAjaxJobGroupList() {
		return new JobGroupDto();
	}
}















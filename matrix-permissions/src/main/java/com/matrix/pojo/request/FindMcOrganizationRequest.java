package com.matrix.pojo.request;

import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindMcOrganizationRequest {
	private static final long serialVersionUID = -8469787574190890690L;
    private String name;
    private Long parentId;
    private Integer type;
    private String redisKey;  // user_name + password
    private McUserInfoView userCache;
    
	public McOrganizationDto buildAjaxTreeList() {
		McOrganizationDto dto = new McOrganizationDto();
		dto.setCid(userCache.getCid());
		dto.setName(name);
		dto.setParentId(parentId);
		dto.setType(type);
		return dto;
	}
}

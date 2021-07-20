package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McOrganizationDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindMcOrganizationRequest  implements Serializable{
	private static final long serialVersionUID = 2614542939937889415L;
	
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

package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindUserRoleListRequest implements Serializable{

	private static final long serialVersionUID = 8895224359209429359L;

	private McUserInfoView userCache;
	
	private Long userId;
	
	private String platform;
	
	private Integer startIndex = 1;
	
	private Integer pageSize = 10;
	
	public McRoleDto buildUserRoleList() {
		McRoleDto dto = new McRoleDto();
		dto.setPlatform(platform);
		dto.setStartIndex(startIndex);
		dto.setPageSize(pageSize);
		return dto;
	}
}















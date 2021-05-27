package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindMcRoleRequest implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	private McUserInfoView userCache;
	
	private String roleName;	// 模糊查询
	
	public McRoleDto buildAjaxSystemRoleList() {
		McRoleDto dto = new McRoleDto();
		dto.setRoleName(roleName);
		return dto;
	}
}















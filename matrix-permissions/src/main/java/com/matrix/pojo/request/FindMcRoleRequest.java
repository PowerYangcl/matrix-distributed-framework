package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class FindMcRoleRequest implements Serializable{

	private static final long serialVersionUID = -6092666198816213055L;

	private McUserInfoView userCache;
	
	private Long id;
	
	private String roleName;	// 模糊查询
	
	public McRoleDto buildAjaxSystemRoleList() {
		McRoleDto dto = new McRoleDto();
		dto.setRoleName(roleName);
		return dto;
	}
	
	public McRole buildAjaxFindRoleInfo() {
		McRole e = new McRole();
		e.setId(id);
		return e;
	}
}















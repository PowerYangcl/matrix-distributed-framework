package com.matrix.pojo.request;

import java.io.Serializable;

import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.view.McUserInfoView;

import lombok.Data;

@Data
public class DeleteMcUserRoleRequest implements Serializable{


	private static final long serialVersionUID = 2801099208669420489L;

	private McUserInfoView userCache;
	
    private Long userId;
    private Long mcRoleId; 
	
	public McUserRoleDto buildDeleteUserRole() {
		McUserRoleDto dto = new McUserRoleDto();
		dto.setUserId(userId);
		dto.setMcRoleId(mcRoleId);
		return dto;
	}
}















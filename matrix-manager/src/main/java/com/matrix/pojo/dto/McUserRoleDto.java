package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class McUserRoleDto extends BaseDto{
	
	private static final long serialVersionUID = -5683799321939616614L;
	
	private Long id;    // managercenter.`mc_user_role` id
	private Long userId;
	private Long mcRoleId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
	}
}

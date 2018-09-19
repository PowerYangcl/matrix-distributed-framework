package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class McRoleFunctionDto extends BaseDto{

	private static final long serialVersionUID = 1L;

	private Long id;
    private Long mcRoleId;
    private Long mcSysFunctionId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
	}
	public Long getMcSysFunctionId() {
		return mcSysFunctionId;
	}
	public void setMcSysFunctionId(Long mcSysFunctionId) {
		this.mcSysFunctionId = mcSysFunctionId;
	} 
}

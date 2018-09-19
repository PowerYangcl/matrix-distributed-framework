package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class McRoleFunction extends BaseEntity{
	private static final long serialVersionUID = -4522812598809666306L;
	private Long id;
    private Long mcRoleId;
    private Long mcSysFunctionId; 
    private String remark;
    
    
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
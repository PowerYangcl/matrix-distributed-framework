package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class McUserRole extends BaseEntity{
	
	private static final long serialVersionUID = 7648418132780885394L;
	
	private Long id;
    private Long mcUserId;
    private Long mcRoleId; 
    private String remark;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMcUserId() {
		return mcUserId;
	}
	public void setMcUserId(Long mcUserId) {
		this.mcUserId = mcUserId;
	}
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
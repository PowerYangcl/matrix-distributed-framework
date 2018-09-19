package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class McRoleView  extends BaseView{
	
	private static final long serialVersionUID = -7615697912055073094L;
	
	private Long id;
    private String roleName;
    private String roleDesc; 
    private String remark;
	
	private Long userId = -1L;  // 一个标识，默认为-1
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}

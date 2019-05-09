package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class McUserRoleView extends BaseView{
	
	private static final long serialVersionUID = -5431173883544525502L;
	
	private Long id;   // managercenter.`mc_user_role` id
	private Long userId;
	private Long roleId;
	private String userName;
	private String roleName;
	private String roleDesc;
	
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
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
}

package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class McRoleView  extends BaseView{
	
	private static final long serialVersionUID = -7615697912055073094L;
	
	private Long id;
	private Long cid;
	private String type;				// leader Leader后台创建的角色|admin 其他平台管理员创建的角色
	private String platform;
    private String roleName;
    private String roleDesc; 
    private String remark;
	
	private Long userId = -1L;  // 一个标识，默认为-1
	private String platformName; // mc_sys_function表的name字段，此时nav_type=0
	
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
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPlatformName() {
		return platformName;
	}
	public void setPlatformName(String platformName) {
		this.platformName = platformName;
	}
}

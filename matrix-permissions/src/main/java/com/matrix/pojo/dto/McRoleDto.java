package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class McRoleDto extends BaseDto{
	
	private static final long serialVersionUID = 6134529834950320057L;
	
	private Long id;
	private Long cid;
	private String type;	// leader Leader后台创建的角色|admin 其他平台管理员创建的角色
	private String platform;
    private String roleName;
    private String roleDesc;
    private String remark;
	private Long userId;
	private Long mcRoleId;
	private String ids;
	private String oldRoleName;
	
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
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getOldRoleName() {
		return oldRoleName;
	}
	public void setOldRoleName(String oldRoleName) {
		this.oldRoleName = oldRoleName;
	}
	
}

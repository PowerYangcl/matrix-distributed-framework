package com.matrix.pojo.cache;

public class McRoleCache {
	private Long mcRoleId;
	private Long cid;
	private String type;
	private String platform;
	private String roleName;
	private String roleDesc;
	private String ids;
	
	public Long getMcRoleId() {
		return mcRoleId;
	}
	public void setMcRoleId(Long mcRoleId) {
		this.mcRoleId = mcRoleId;
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
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
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
}

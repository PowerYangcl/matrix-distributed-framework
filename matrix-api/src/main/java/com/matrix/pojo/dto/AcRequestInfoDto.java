package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class AcRequestInfoDto extends BaseDto {
	
	private static final long serialVersionUID = -4668467595285763917L;
	private Long id;
    private String organization;
    private String key;
    private String value;
    private String atype;
    private Integer flag;
	private Integer isallot;  // 如果是第三方请求者，标识在编辑方法中是否为分配系统开放接口。0：否|1：是
	private String targets;		// API的自增id，逗号分隔

	public Integer getIsallot() {
		return isallot;
	}
	public void setIsallot(Integer isallot) {
		this.isallot = isallot;
	}
	public String getTargets() {
		return targets;
	}
	public void setTargets(String targets) {
		this.targets = targets;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAtype() {
		return atype;
	}
	public void setAtype(String atype) {
		this.atype = atype;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}










































package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class AcRequestInfo extends BaseEntity{
	
	private static final long serialVersionUID = 7057314343184384965L;
	
	private Long id;
    private String organization;
    private String key;
    private String value;
    private String atype;
    private Integer flag;
    
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
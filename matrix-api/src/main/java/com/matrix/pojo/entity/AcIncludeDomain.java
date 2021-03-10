package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class AcIncludeDomain extends BaseEntity{
	
	private static final long serialVersionUID = 5554042040317076190L;
	
	private Long id;
    private String domain;
    private String companyName;
    private String project;
    private Integer flag;
    private String remark;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}


















































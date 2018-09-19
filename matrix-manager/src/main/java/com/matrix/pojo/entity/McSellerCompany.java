package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class McSellerCompany extends BaseEntity{
	
	private static final long serialVersionUID = -8869116017964897410L;
	
	private Long id;
    private String mcCompanyName;
    private String remark;
    private Integer symbiosis;  // 合作关系
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMcCompanyName() {
		return mcCompanyName;
	}
	public void setMcCompanyName(String mcCompanyName) {
		this.mcCompanyName = mcCompanyName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getSymbiosis() {
		return symbiosis;
	}
	public void setSymbiosis(Integer symbiosis) {
		this.symbiosis = symbiosis;
	}
}
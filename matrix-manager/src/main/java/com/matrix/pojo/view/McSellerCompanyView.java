package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class McSellerCompanyView extends BaseView{

	private static final long serialVersionUID = -2275629156971139686L;

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

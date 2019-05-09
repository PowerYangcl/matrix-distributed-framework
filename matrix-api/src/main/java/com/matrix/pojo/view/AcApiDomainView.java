package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class AcApiDomainView extends BaseView{
	private static final long serialVersionUID = -4082158175040561474L;
	
	private Long id;
    private String domain;
    private String companyName;
    
    
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
}





























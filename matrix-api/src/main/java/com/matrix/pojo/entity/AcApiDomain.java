package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class AcApiDomain extends BaseEntity{
	
	private static final long serialVersionUID = -2245878037084261448L;
	private Long id;
    private Long acApiInfoId;
    private Long acIncludeDomainId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAcApiInfoId() {
		return acApiInfoId;
	}
	public void setAcApiInfoId(Long acApiInfoId) {
		this.acApiInfoId = acApiInfoId;
	}
	public Long getAcIncludeDomainId() {
		return acIncludeDomainId;
	}
	public void setAcIncludeDomainId(Long acIncludeDomainId) {
		this.acIncludeDomainId = acIncludeDomainId;
	}
}




























package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class AcApiDomainDto extends BaseDto{

	private static final long serialVersionUID = 6363122362845818357L;
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




































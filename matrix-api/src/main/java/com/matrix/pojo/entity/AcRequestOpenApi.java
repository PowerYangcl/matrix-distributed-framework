package com.matrix.pojo.entity;


import com.matrix.base.BaseEntity;

public class AcRequestOpenApi extends BaseEntity{
	
	private static final long serialVersionUID = -7322646120127235047L;
	
	private Long id;
    private Long acRequestInfoId;
    private Long acApiInfoId;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAcRequestInfoId() {
		return acRequestInfoId;
	}
	public void setAcRequestInfoId(Long acRequestInfoId) {
		this.acRequestInfoId = acRequestInfoId;
	}
	public Long getAcApiInfoId() {
		return acApiInfoId;
	}
	public void setAcApiInfoId(Long acApiInfoId) {
		this.acApiInfoId = acApiInfoId;
	}
}
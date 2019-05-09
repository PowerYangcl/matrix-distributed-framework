package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class AcRequestOpenApiView extends BaseView{
	
	private static final long serialVersionUID = -329476233321236306L;
	
	private Long acApiInfoId;
	private String name;
	private String target;
	
	public Long getAcApiInfoId() {
		return acApiInfoId;
	}
	public void setAcApiInfoId(Long acApiInfoId) {
		this.acApiInfoId = acApiInfoId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	
}

package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class McUserInfoOrganizationDto extends BaseDto {

	private static final long serialVersionUID = 3149789333433085578L;
	
	private Long id;
    private Long cid;
    private Long mcUserInfoId;
    private Long mcOrganizationId;
    private String platform;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Long getMcUserInfoId() {
		return mcUserInfoId;
	}
	public void setMcUserInfoId(Long mcUserInfoId) {
		this.mcUserInfoId = mcUserInfoId;
	}
	public Long getMcOrganizationId() {
		return mcOrganizationId;
	}
	public void setMcOrganizationId(Long mcOrganizationId) {
		this.mcOrganizationId = mcOrganizationId;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
}

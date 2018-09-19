package com.matrix.pojo.dto;

import java.util.Date;

import com.matrix.base.BaseDto;

public class McUserInfoDto extends BaseDto{
	
	private static final long serialVersionUID = 4629549271113492123L;
	
	private Long id;
    private Long mcSellerCompanyId;
    private String userName;
    private Date createTime;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getMcSellerCompanyId() {
		return mcSellerCompanyId;
	}
	public void setMcSellerCompanyId(Long mcSellerCompanyId) {
		this.mcSellerCompanyId = mcSellerCompanyId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
    
}
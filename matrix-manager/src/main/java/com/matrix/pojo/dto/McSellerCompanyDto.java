package com.matrix.pojo.dto;


import com.matrix.base.BaseDto;

public class McSellerCompanyDto extends BaseDto{
	
	private static final long serialVersionUID = 8389571879483149642L;
	
	private Long id;
    private String mcCompanyName;
    
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
    
}
package com.matrix.pojo.dto;

import java.io.Serializable;
import java.util.List;

import com.matrix.base.BaseDto;
import com.matrix.pojo.entity.CompanyAgeGroup;

public class CompanyAgeGroupDto extends BaseDto implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7191840273084959007L;

    /** 客户id*/
    private Long cid;
    /**
     * 年龄段
     */
    private List<CompanyAgeGroup>groups;

	public List<CompanyAgeGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<CompanyAgeGroup> groups) {
		this.groups = groups;
	}

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}
	
    
}
package com.matrix.pojo.entity;

import java.io.Serializable;

import com.matrix.base.BaseEntity;

public class CompanyAgeGroup extends BaseEntity implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7477266399874640444L;

	/** 公司设置年龄段字典*/
    private Long id;

    /** 客户id*/
    private Long cid;

    
    /** 年龄段名称*/
    private String name;

    /** 开始年龄*/
    private Integer startAge;

    /** 结束年龄*/
    private Integer endAge;

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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }
}
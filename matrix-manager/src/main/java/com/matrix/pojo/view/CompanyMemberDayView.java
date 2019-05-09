package com.matrix.pojo.view;

import java.io.Serializable;

import com.matrix.base.BaseView;

public class CompanyMemberDayView extends BaseView implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 1003312753066624485L;

	/** 公司信息-会员日定义*/
    private Long id;

    /** 客户id*/
    private Long cid;

    /** 时间类型 1每年，2每月，3每周*/
    private Integer timeType;

    /** 开始时间*/
    private String startTime;

    /** 结束时间*/
    private String endTime;

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

    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }
}
package com.matrix.pojo.entity;

import com.matrix.base.BaseEntity;

public class JobGroup extends BaseEntity{
	private static final long serialVersionUID = 3028845302777371307L;
	private Long id;
    private String groupName;
    private String ip;
    private String remark;

    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}
package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class JobGroupDto extends BaseDto {
	private static final long serialVersionUID = -3620872890447524355L;
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

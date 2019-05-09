package com.matrix.pojo.view;

import com.matrix.base.BaseView;

public class JobGroupView extends BaseView {
	private static final long serialVersionUID = -5409407175550884086L;
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

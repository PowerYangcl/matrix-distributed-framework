package com.matrix.pojo.view;

import java.util.Date;

import com.matrix.base.BaseView;

public class JobExecLogView extends BaseView {
	private static final long serialVersionUID = -548057390135669295L;
	private Long id;
    private String jobName;
    private String jobTitle;
    private String status;
    private String ip;
    private Long runGroupId;
    private String runGroupName;
    private Date beginTime;
    private Date endTime;
    private String remark;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Long getRunGroupId() {
		return runGroupId;
	}
	public void setRunGroupId(Long runGroupId) {
		this.runGroupId = runGroupId;
	}
	public String getRunGroupName() {
		return runGroupName;
	}
	public void setRunGroupName(String runGroupName) {
		this.runGroupName = runGroupName;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}

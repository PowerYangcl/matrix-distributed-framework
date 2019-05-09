package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

public class JobInfo extends BaseEntity{
	private static final long serialVersionUID = -8848133833768861972L;
	private Long id;
    private String jobName;
    private String jobTitle;
    private String jobClass;
    private String jobTriger;
    private Long runGroupId;
    private Date beginTime;
    private Date endTime;
    private Date nextTime;
    private Integer pause;
    private String lockKey;
    private Integer expireTime;
    private Integer timeOut;
    private String jobList; 
    private Integer trigerType;
    private Integer logType;
    private String remark;
    
    // 目前不用的字段，但保留扩展功能
    private Integer concurrentType;
    private Integer maxExecTime;
    
    
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
	public String getJobClass() {
		return jobClass;
	}
	public void setJobClass(String jobClass) {
		this.jobClass = jobClass;
	}
	public String getJobTriger() {
		return jobTriger;
	}
	public void setJobTriger(String jobTriger) {
		this.jobTriger = jobTriger;
	}
	public Long getRunGroupId() {
		return runGroupId;
	}
	public void setRunGroupId(Long runGroupId) {
		this.runGroupId = runGroupId;
	}
	public Integer getConcurrentType() {
		return concurrentType;
	}
	public void setConcurrentType(Integer concurrentType) {
		this.concurrentType = concurrentType;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Integer getMaxExecTime() {
		return maxExecTime;
	}
	public void setMaxExecTime(Integer maxExecTime) {
		this.maxExecTime = maxExecTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getNextTime() {
		return nextTime;
	}
	public void setNextTime(Date nextTime) {
		this.nextTime = nextTime;
	}
	public Integer getPause() {
		return pause;
	}
	public void setPause(Integer pause) {
		this.pause = pause;
	}
	public String getLockKey() {
		return lockKey;
	}
	public void setLockKey(String lockKey) {
		this.lockKey = lockKey;
	}
	public Integer getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Integer expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(Integer timeOut) {
		this.timeOut = timeOut;
	}
	public String getJobList() {
		return jobList;
	}
	public void setJobList(String jobList) {
		this.jobList = jobList;
	}
	public Integer getTrigerType() {
		return trigerType;
	}
	public void setTrigerType(Integer trigerType) {
		this.trigerType = trigerType;
	}
	public Integer getLogType() {
		return logType;
	}
	public void setLogType(Integer logType) {
		this.logType = logType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
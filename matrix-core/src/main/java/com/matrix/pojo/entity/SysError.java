package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

public class SysError  extends BaseEntity{
	private static final long serialVersionUID = 6576073949611320570L;
	
	private Long zid;
	private String uid;
	private String errorCode;
	private String errorType;
	private String errorLevel;
	private String errorSource;
	private String errorInfo;
//	private Date createTime;
	
	public Long getZid() {
		return zid;
	}
	public void setZid(Long zid) {
		this.zid = zid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getErrorLevel() {
		return errorLevel;
	}
	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}
	public String getErrorSource() {
		return errorSource;
	}
	public void setErrorSource(String errorSource) {
		this.errorSource = errorSource;
	}
	public String getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
//	public Date getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(Date createTime) {
//		this.createTime = createTime;
//	}
	
	
}

package com.matrix.pojo.entity;

import java.util.Date;

import com.matrix.base.BaseEntity;

public class SysTableOpreateRecord extends BaseEntity{
	private static final long serialVersionUID = -7609538250907712423L;
	
	
	public SysTableOpreateRecord() {
	}
	
	public SysTableOpreateRecord(Long cid, String mdvalue, Long zid, String tableName) {
		this.cid = cid;
		this.mdvalue = mdvalue;
		this.zid = zid;
		this.tableName = tableName;
	}
	private Long id;
    private Long cid;
    private String mdvalue;
    private Long zid;
    private String tableName;
    private Date createTime;
    private Long createUserId;
    private String createUserName;
    private Date updateTime;
    private Long updateUserId;
    private String updateUserName;
    private Integer deleteFlag;
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
	public String getMdvalue() {
		return mdvalue;
	}
	public void setMdvalue(String mdvalue) {
		this.mdvalue = mdvalue;
	}
	public Long getZid() {
		return zid;
	}
	public void setZid(Long zid) {
		this.zid = zid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public String getUpdateUserName() {
		return updateUserName;
	}
	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
}
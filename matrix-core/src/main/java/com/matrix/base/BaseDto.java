package com.matrix.base;

import java.io.Serializable;
import java.util.Date;

import com.matrix.pojo.view.McUserInfoView;

/**
 * @description: 数据传输模型基类 | 提供dubbo分布式序列化支持|提供dubbo分布式分页支持
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年7月24日 上午2:44:55 
 * @version 1.0.0.1
 */
public class BaseDto implements Serializable {
	
	private static final long serialVersionUID = -7666209205055824390L;

	/** 
	 * 起始页数 - 微服务化项目需要的字段
	 */
	private Integer startIndex = 1;
	
	/**
	 *  分页记录数 - 微服务化项目需要的字段
	 */
	private Integer pageSize = 10;
	
	
	/**
	 *  动态列表排序依据，比如：dto.setOrder("create_time");
	 */
	private String orderBy = null;

	
	private Date createTime;
	private Long createUserId;
	private String createUserName;
	private Date updateTime;
	private Long updateUserId;
	private String updateUserName;
	private Integer deleteFlag;               // 0 删除 | 1 未删除 数据库记录默认未删除

	
	private McUserInfoView userCache ;
	/*
	 * 分布式事务 xid
	 * */
	private String commodityCode;
	
	private String eleValue; // 按钮权限标识

	public McUserInfoView getUserCache() {
		return userCache;
	}
	public void setUserCache(McUserInfoView userCache) {
		this.userCache = userCache;
	}
	public Integer getStartIndex() {
		return startIndex;
	}
	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
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
	public String getCommodityCode() {
		return commodityCode;
	}
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
	public String getEleValue() {
		return eleValue;
	}
	public void setEleValue(String eleValue) {
		this.eleValue = eleValue;
	}
}






















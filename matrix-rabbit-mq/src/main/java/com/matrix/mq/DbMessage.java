package com.matrix.mq;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *@description:暂存到数据库的消息
 *
 *@author Sjh
 *@date 2019/3/16 11:47
 *@version 1.0.1
 */

public class DbMessage implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String exchange;
	private String routing;
	private String message;
	private Integer state;
	private Date modifyTime;
	private Date createTime;
	public DbMessage(){}

	public DbMessage(String exchange, String routing, String message,Date createTime){
		super();
		this.exchange = exchange;
		this.routing = routing;
		this.message = message;
		this.createTime=createTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
	}

	public String getRouting() {
		return routing;
	}

	public void setRouting(String routing) {
		this.routing = routing;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

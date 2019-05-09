package com.matrix.pojo.dto;

import com.matrix.base.BaseDto;

public class PowerCacheDto extends BaseDto {
	private static final long serialVersionUID = 6023521190670157848L;
	
	private String cacheName;  // Cache 对象的名称
	private String key;		// 一级缓存中的Key
	private String value;	// 一级缓存中对应的值
	private String dubboAddr; // dubbo节点IP+端口号
	private String batch;  // 批量添加、更新或删除所携带的key:value值，逗号分隔
	
	public String getCacheName() {
		return cacheName;
	}
	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDubboAddr() {
		return dubboAddr;
	}
	public void setDubboAddr(String dubboAddr) {
		this.dubboAddr = dubboAddr;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
}

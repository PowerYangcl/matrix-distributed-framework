package com.matrix.pojo.dto;

import org.apache.http.HttpEntity;

public class WebClientRequest {
	// 文件路径
	private String filePath = "";
	// 请求的url
	private String url = "";
	// 请求的实体
	private HttpEntity httpEntity = null;
	// 密码
	private String password = "";
	// 内容类型 application/json
	private String conentType = "";

	
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public HttpEntity getHttpEntity() {
		return httpEntity;
	}
	public void setHttpEntity(HttpEntity httpEntity) {
		this.httpEntity = httpEntity;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConentType() {
		return conentType;
	}
	public void setConentType(String conentType) {
		this.conentType = conentType;
	}
}


















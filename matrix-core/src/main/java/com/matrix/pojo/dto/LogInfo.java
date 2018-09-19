package com.matrix.pojo.dto;

public class LogInfo {

	/**
	 * 日志类型
	 */
	private String type = "";

	/**
	 * 日志内容
	 */
	private String info = "";

	/**
	 * 创建时间
	 */
	private String create = "";

	/**
	 * 日志编号
	 */
	private String uid = "";

	/**
	 * 服务器
	 */
	private String server = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getCreate() {
		return create;
	}

	public void setCreate(String create) {
		this.create = create;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

}

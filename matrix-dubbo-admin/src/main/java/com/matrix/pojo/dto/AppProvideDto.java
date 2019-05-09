package com.matrix.pojo.dto;

public class AppProvideDto {
	private Long id; // 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID，查看该RPC接口的消费者列表需要使用到这个参数。
	private String application; // 服务名称
	private String service;		// com.scrm.services.aem.rpc.IStaffScoreDetailRpcService
	private String protocol;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	} 
	
}

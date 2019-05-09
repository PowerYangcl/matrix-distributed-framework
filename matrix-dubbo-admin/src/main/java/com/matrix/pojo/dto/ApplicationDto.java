package com.matrix.pojo.dto;

public class ApplicationDto {

	private Long id; // 具体到Dubbo服务所提供的每一个RPC接口，会对应一个长整型ID，查看该RPC接口的消费者列表需要使用到这个参数。
	private String application; // 服务名称
	private String username; // 服务拥有者
	private Integer type; // 服务类型：提供者、消费者、全部
	private String nodeAddress;    // host or ip address
	
	private String serviceKey;		// com.scrm.services.aem.rpc.IStaffScoreDetailRpcService
	private Boolean dynamic;  // true 动态  false 静态
	private Boolean enabled;		// true 已启用		false 已禁用
	
	
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNodeAddress() {
		return nodeAddress;
	}
	public void setNodeAddress(String nodeAddress) {
		this.nodeAddress = nodeAddress;
	}
	public String getServiceKey() {
		return serviceKey;
	}
	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}
	public Boolean getDynamic() {
		return dynamic;
	}
	public void setDynamic(Boolean dynamic) {
		this.dynamic = dynamic;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	
}

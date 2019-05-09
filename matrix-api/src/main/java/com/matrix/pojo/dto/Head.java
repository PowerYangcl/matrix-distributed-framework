package com.matrix.pojo.dto;

public class Head {
	private String key;
	private String value;
	private String target; // 所开放的接口名称
	private String client; // 请求客户端类型。0:IOS   1:Android   2:微信   3:服务器
	private String version; // 客户端版本
	private String requestTime; // 请求发起时间
	private String channel;  // 通路；比如乘客端|司机端|超级车 等等
	
	// 对应手机端的用户缓存信息的key，且client类型为0:IOS   1:Android   2:微信，atype为private
	private String accessToken;   // 令牌。针对客户端用户登录使用；非数据库字段，每小时删除一次。[用户名+密码(md5)](md5) + 时间戳

	
	public String getChannel() {
		return channel;
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
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}

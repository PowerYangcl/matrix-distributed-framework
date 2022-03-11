package com.matrix.base;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ApiHead {
	
	@NotBlank(message = "600010016")	// 600010016=非法的请求数据结构，缺少key或value
	private String key;
	
	@NotBlank(message = "600010016")
	private String value;
	
	@NotBlank(message = "600010003")		// 600010003 非法的请求数据结构，缺少所访问的接口名。
	private String target; // 所开放的接口名称
	
	private String client; // 请求客户端类型。0:IOS   1:Android   2:微信   3:服务器
	
	private String version; // 客户端版本
	
	private String requestTime; // 请求发起时间
	
	private String channel;  // 通路；比如乘客端|司机端|超级车 等等
	
	// 对应手机端的用户缓存信息的key，且client类型为0:IOS   1:Android   2:微信，atype为private
	private String accessToken;   // 令牌。针对客户端用户登录使用；非数据库字段，每小时删除一次。[用户名+密码(md5)](md5) + 时间戳

	private Long cid; // 当前请求来自哪个店铺，兼容多店铺的情况
}

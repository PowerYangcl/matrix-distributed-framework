package com.matrix.cache.redis.core.mode;

import com.matrix.base.BaseClass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: redis 配置信息
 * 
 * @author Yangcl
 * @date 2023-5-9 17:51:35
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.RedisProperty.java
 * @version v1.6.1.8-redis-config-yaml
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class RedisConnectionProperty extends BaseClass{  //  extends BaseClass

	private String model;  		// standalone|master-replica|sentinel|cluster
	
	private String host;			// ip or url
	
	private String port; 			// redis port
	
	private String name; 		// redis connection name v6.0以上版本
	
	private String password;  // redis connection pwd
	
	private String sentinelPassword;
	
	private String sentinelMasterId;
	
	private String defalutDb; 	// 
}

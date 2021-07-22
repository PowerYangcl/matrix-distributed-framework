package com.matrix.cache.redisson;

import com.matrix.base.BaseClass;

/**
 * @description: Redisson核心配置，用于提供初始化的redisson实例
 * 
 * @author Yangcl
 * @date 2021-7-21 16:06:28
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.RedissonManager.java
 * @version 1.0.0.1
 */
public class RedissonManager extends BaseClass{

	private String config = "standalone";	// 默认为单机模式
	
	public RedissonManager() {
		config = this.getConfig("matrix-cache.cache_model_" + this.getConfig("matrix-core.model"));
	}

}

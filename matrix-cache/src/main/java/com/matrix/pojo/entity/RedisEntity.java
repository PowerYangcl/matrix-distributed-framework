package com.matrix.pojo.entity;

import lombok.Data;

/**
 * @description: Redis实体存储对象
 * 
 * @author Yangcl
 * @date 2021-2-7 11:19:51
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redis.core.mode.RedisEntity.java
 * @version 1.0.0.1
 */
@Data
public class RedisEntity {
	private String key;
	private String value;
}

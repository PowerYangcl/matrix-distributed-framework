package com.matrix.launch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.matrix.cache.redis.core.mode.RedisConnectionProperty;

@Configuration
public class RedisPropertyConfig {
	
	@Bean
	@ConditionalOnMissingBean
	public RedisConnectionProperty redisConnectionProperty(
			@Value("${spring.redis.model}") String model,
			@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") String port,
			@Value("${spring.redis.name}") String name,
			@Value("${spring.redis.password}") String password,
			@Value("${spring.redis.sentinelPassword}") String sentinelPassword,
			@Value("${spring.redis.sentinelMasterId}") String sentinelMasterId,
			@Value("${spring.redis.database}") String database  ) {
		RedisConnectionProperty entity = new RedisConnectionProperty();
		entity.setModel(model);
		entity.setHost(host);
		entity.setPort(port);
		entity.setName(name);
		entity.setPassword(password);
		entity.setSentinelPassword(sentinelPassword);
		entity.setSentinelMasterId(sentinelMasterId);
		entity.setDefalutDb(database);
		return entity;
	}
	
}

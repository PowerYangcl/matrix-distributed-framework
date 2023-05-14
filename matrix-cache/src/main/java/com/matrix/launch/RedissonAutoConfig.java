package com.matrix.launch;

import org.redisson.Redisson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import com.matrix.cache.redis.core.mode.RedisConnectionProperty;
import com.matrix.cache.redisson.DistributedLockHandler;
import com.matrix.support.RedissonLock;

/**
 * @description: Redisson自动化配置
 * 
 * @author Yangcl
 * @date 2021-7-23 16:31:48
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.launch.RedissonAutoConfig.java
 * @version 1.6.0.4-redisson
 */
@Configuration
@ConditionalOnClass(Redisson.class)
public class RedissonAutoConfig {

	@Bean
	@Order(value = 1)
    @ConditionalOnMissingBean
    @DependsOn(value = {"tx-advice-advisor"})
    public RedissonLock redissonLock(
    		@Value("${spring.redis.model}") String model,
			@Value("${spring.redis.host}") String host,
			@Value("${spring.redis.port}") String port,
			@Value("${spring.redis.name}") String name,
			@Value("${spring.redis.password}") String password,
			@Value("${spring.redis.sentinelPassword}") String sentinelPassword,
			@Value("${spring.redis.sentinelMasterId}") String sentinelMasterId,
			@Value("${spring.redis.database}") String database ) {
		RedisConnectionProperty entity = new RedisConnectionProperty();
		entity.setModel(model);
		entity.setHost(host);
		entity.setPort(port);
		entity.setName(name);
		entity.setPassword(password);
		entity.setSentinelPassword(sentinelPassword);
		entity.setSentinelMasterId(sentinelMasterId);
		entity.setDefalutDb(database);
		
        RedissonLock redissonLock = new RedissonLock(entity);
        return redissonLock;
    }
	
	@Bean
	@Order(value = 2)
	public DistributedLockHandler lockHandler() {
		return new DistributedLockHandler();
	}
}

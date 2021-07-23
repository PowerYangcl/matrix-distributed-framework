package com.matrix.launch;

import org.redisson.Redisson;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.core.annotation.Order;

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

	@Bean(name = "redissonLock")
    @ConditionalOnMissingBean		// @Order(value = 1)
    public RedissonLock redissonLock() {
        RedissonLock redissonLock = new RedissonLock();
        return redissonLock;
    }
}

package com.matrix.cache.redisson.mode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.pojo.properties.RedissonProperties;

/**
 * @description: 单机部署Redisson配置
 * 
 * @author Yangcl
 * @date 2021-7-22 16:54:46
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.mode.StandaloneConfigImpl.java
 * @version 1.6.0.4-redisson
 */
@Slf4j
public class StandaloneConfigImpl implements IRedissonConfigService {

	@Override
	public Config createRedissonConfig(RedissonProperties redissonProperties) {
		Config config = new Config();
		try {
			String address = redissonProperties.getAddress();
			String username = redissonProperties.getUsername();
			String password = redissonProperties.getPassword();
			int database = redissonProperties.getDatabase();
			String redisAddr = "redis://" + address;

			config.useSingleServer().setAddress(redisAddr);
			config.useSingleServer().setDatabase(database);
			// 密码可以为空
			if (StringUtils.isNotBlank(password)) {
				config.useSingleServer().setPassword(password);
			}
			if (StringUtils.isNotBlank(username)) {
				config.useSingleServer().setUsername(username);
			}
			log.info("初始化[单机部署]方式Config,redisAddress:" + address);
		} catch (Exception e) {
			log.error("单机部署 Redisson init error", e);
		}
		return config;
	}
}









































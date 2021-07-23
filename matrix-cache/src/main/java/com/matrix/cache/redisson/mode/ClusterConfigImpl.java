package com.matrix.cache.redisson.mode;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.pojo.properties.RedissonProperties;

/**
 * @description: 集群方式Redisson部署；cluster方式至少6个节点(3主3从，3主做sharding，3从用来保证主宕机后可以高可用)
 * 		格式为: 127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381,127.0.0.1:6382,127.0.0.1:6383,127.0.0.1:6384
 * 
 * @author Yangcl
 * @date 2021-7-22 15:57:57
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.mode.ClusterConfigImpl.java
 * @version 1.6.0.4-redisson
 */
@Slf4j
public class ClusterConfigImpl implements IRedissonConfigService {

	@Override
    public Config createRedissonConfig(RedissonProperties redissonProperties){
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String username = redissonProperties.getUsername();
            String password = redissonProperties.getPassword();
            String[] addrTokens = address.split(",");
            
            // 设置cluster节点的服务IP和端口
            for (int i = 0; i < addrTokens.length; i++) {
                config.useClusterServers().addNodeAddress("redis://" + addrTokens[i]);
            }
            if (StringUtils.isNotBlank(password)) {
                config.useClusterServers().setPassword(password);
            }
            if(StringUtils.isNotBlank(username)) {
            	config.useClusterServers().setUsername(username);
            }
            log.info("初始化[集群部署]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("集群部署 Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }
}












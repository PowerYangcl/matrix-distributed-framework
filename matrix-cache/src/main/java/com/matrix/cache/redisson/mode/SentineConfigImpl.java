package com.matrix.cache.redisson.mode;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.pojo.properties.RedissonProperties;

/**
 * @description: 哨兵集群部署Redis连接配置
 *			格式：sentinel.conf 配置里的sentinel别名, sentinel1节点的服务IP和端口，sentinel2节点的服务IP和端口，sentinel3节点的服务IP和端口
 *			比如：sentinel.conf 里配置为sentinel monitor my-sentinel-name 127.0.0.1 6379 2,那么这里就配置my-sentinel-name
 *			例如：my-sentinel-name,127.0.0.1:26379,127.0.0.1:26389,127.0.0.1:26399
 * 
 * @author Yangcl
 * @date 2021-7-22 16:35:24
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.mode.SentineConfigImpl.java
 * @version 1.6.0.4-redisson
 */
@Slf4j
public class SentineConfigImpl implements IRedissonConfigService {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String username = redissonProperties.getUsername();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String sentinelAliasName = addrTokens[0];
            
            // 设置redis配置文件sentinel.conf配置的sentinel别名
            config.useSentinelServers().setMasterName(sentinelAliasName);
            config.useSentinelServers().setDatabase(database);
            if (StringUtils.isNotBlank(password)) {
                config.useSentinelServers().setPassword(password);
            }
            if(StringUtils.isNotBlank(username)) {
            	config.useSentinelServers().setUsername(username);
            }
            
            // 设置sentinel节点的服务IP和端口
            for (int i = 1; i < addrTokens.length; i++) {		// TODO 是否需要remove第一个元素
                config.useSentinelServers().addSentinelAddress("redis://" + addrTokens[i]);
            }
            log.info("初始化[哨兵部署]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("哨兵部署 Redisson init error", e);

        }
        return config;
    }
}
































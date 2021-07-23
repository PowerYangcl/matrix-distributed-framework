package com.matrix.cache.redisson.mode;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;

import com.matrix.cache.inf.IRedissonConfigService;
import com.matrix.pojo.properties.RedissonProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 主从部署Redisson配置；连接方式:  主节点,子节点,子节点。
 * 		注意：主节点要在第一个。
 * 		格式：127.0.0.1:6379,127.0.0.1:6380,127.0.0.1:6381
 * 
 * @author Yangcl
 * @date 2021-7-22 16:05:27
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.cache.redisson.mode.MasterslaveConfigImpl.java
 * @version 1.6.0.4-redisson
 */
@Slf4j
public class MasterslaveConfigImpl implements IRedissonConfigService {

    @Override
    public Config createRedissonConfig(RedissonProperties redissonProperties) {
        Config config = new Config();
        try {
            String address = redissonProperties.getAddress();
            String username = redissonProperties.getUsername();
            String password = redissonProperties.getPassword();
            int database = redissonProperties.getDatabase();
            String[] addrTokens = address.split(",");
            String masterNodeAddr = addrTokens[0];	// TODO 是否需要"redis://"前缀？？
            
            // 设置主节点ip
            config.useMasterSlaveServers().setMasterAddress(masterNodeAddr);
            if (StringUtils.isNotBlank(password)) {
                config.useMasterSlaveServers().setPassword(password);
            }
            if(StringUtils.isNotBlank(username)) {
            	config.useMasterSlaveServers().setUsername(username);
            }
            config.useMasterSlaveServers().setDatabase(database);
            
            // 设置从节点，移除第一个节点，默认第一个为主节点
            List<String> slaveList = new ArrayList<>();
            for (String addrToken : addrTokens) {
                slaveList.add("redis://" + addrToken);
            }
            slaveList.remove(0);
            
            config.useMasterSlaveServers().addSlaveAddress(slaveList.toArray(new String[]{}));
            log.info("初始化[主从部署]方式Config,redisAddress:" + address);
        } catch (Exception e) {
            log.error("主从部署 Redisson init error", e);
            e.printStackTrace();
        }
        return config;
    }

}























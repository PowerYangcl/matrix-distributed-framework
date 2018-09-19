package com.matrix.cache.redis.core;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseClass;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.map.MDataMap;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisCommands;

/**
 * @description: 无需密码支持的Redis集群实例|此类废弃
 *
 * @author Yangcl
 * @date 2018年9月18日 下午3:56:03 
 * @version 1.0.0.1
 */
@Deprecated
public class RedisCall extends BaseClass  {  // implements ICacheFactory

	private JedisCommands commands = null;
	
	public RedisCall() {
		String url = getConfig("matrix-cache.cache_url_" + this.getConfig("matrix-core.model") );
		if (StringUtils.contains(url , ",")) {
			Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();    // Jedis Cluster will attempt to discover cluster nodes automatically
			for (String s : StringUtils.split(url , ",")) {
				String[] sHost = StringUtils.split(s , ":");
				jedisClusterNodes.add(new HostAndPort(sHost[0], Integer.valueOf(sHost[1])));
			}

			JedisCluster jc = new JedisCluster(jedisClusterNodes);
			this.setCommonds(jc);
		}
	}
	

	public JedisCommands getCommonds() {
		return commands;
	}
	public void setCommonds(JedisCommands jedisCommands) {
		commands = jedisCommands;
	}

	
	public String hget(final String key, final String field) {
		return getCommonds().hget(key, field);
	}

	public Long hdel(final String key, final String field) {
		return getCommonds().hdel(key, field);
	}

	public Long hset(String key, String field, String value) {
		return getCommonds().hset(key, field, value);
	}

	public String set(String key, String value) {
		return getCommonds().set(key, value);
	}

	public String get(String key) {
		return getCommonds().get(key);
	}

	public Long incrBy(String key, long integer) {
		return getCommonds().incrBy(key, integer);
	}

	public String setex(String key, int seconds, String value) {
		return getCommonds().setex(key, seconds, value);
	}

	public Long setnx(String key, String value) {
		return getCommonds().setnx(key, value);
	}

	public Long del(String key) {
		return getCommonds().del(key);
	}

	public Boolean exists(String key) {
		return getCommonds().exists(key);
	}

	public MDataMap hgetAll(String key) {
		MDataMap mDataMap = new MDataMap();
		mDataMap.putAll(getCommonds().hgetAll(key));
		return mDataMap;
	}

	public Long hincrBy(String key, String field, long value) {
		return getCommonds().hincrBy(key, field, value);
	}

	public Long expire(String key, int seconds) {
		return getCommonds().expire(key, seconds);
	}

	public Long ttl(String key) {
		return getCommonds().ttl(key);
	}
}

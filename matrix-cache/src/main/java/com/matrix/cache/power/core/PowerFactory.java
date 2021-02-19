package com.matrix.cache.power.core;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.entity.RedisEntity;
import com.matrix.system.cache.PowerCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @description: 针对缓存提供基本的增删改查操作|非分布式系统可以使用此缓存，用于快速开发
 * 		项目后期可以进行无损迁移。此类型缓存只支持String字符串操作。
 *
 * @author Yangcl
 * @date 2018年11月18日 下午8:43:44 
 * @version 1.0.0.1
 */
public class PowerFactory implements ICacheFactory {
	
	private Cache cache;
	private String baseKey = "";
	private String load = "";  // ILoadCache		
	public PowerFactory(String key , String load) {
		if(StringUtils.isBlank(load)) {
			load = "";
		}
		baseKey = key;
		this.load = "com.matrix.load." + load;
		cache = PowerCache.getInstance().addPowerCache("power");
	}
	
	/**
	 * @description: 返回基础的key前缀
	 *
	 * @author Yangcl
	 * @date 2018年12月17日 下午10:38:35 
	 * @version 1.0.0.1
	 */
	public String getBaseKey() {
		return baseKey;
	}

	/////////////////////////////////////////////////////////////////// 基础json //////////////////////////////////////////////////////////////////////
	public String get(String key) {
		String value = this.findInEhcache(baseKey + key);
		if(StringUtils.isBlank(value)) {
			synchronized (CacheLaunch.class) {
				value = this.findInEhcache(baseKey + key);
				if(StringUtils.isBlank(value)) {
					if(this.load.length() == 16) {
						return "";
					}
					try {
						Class<?> clazz = Class.forName(load);   
						if (clazz != null && clazz.getDeclaredMethods() != null){
							@SuppressWarnings("unchecked")
							ILoadCache<String> cache = (ILoadCache<String>) clazz.newInstance();
							return cache.load(key , "").toString();
						}else {
							return "";
						}
					}catch (Exception e) {
						e.printStackTrace();
						return "";
					}    
				}else {
					return value;
				}
			}
		}else {
			return value;
		}
	}
	
	public Boolean set(String key, String value) {
		key = baseKey + key;
		if(this.containsKey(key)) {
			return true;
		}
		cache.put(new Element(key, value));
		return true;
	}
	
	public Boolean set(String key, String value, long expire) {
		return this.set(key, value);
	}

	/**
	 * @description: 判断ehcache中是否包含这个Key
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月18日 下午9:03:37 
	 * @version 1.0.0.1
	 */
	private boolean containsKey(String key) {
		return cache.isKeyInCache(key);
	}
	
	/**
	 * @description: 从ehcache中直接获取缓存对应的值
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月18日 下午9:04:09 
	 * @version 1.0.0.1
	 */
	private String findInEhcache(String key) {
		if(!this.containsKey(key)) {
			return "";
		}
		return (String) cache.get(key).getObjectValue();
	}
	
	/**
	 * @description: 返回 key 在一级缓存中所关联的字符串值，如果没取到则从二级缓存中尝试获取
	 *
	 * @param key 如果 key 储存的值不是字符串类型，返回一个错误，因为 GET 只能用于处理字符串值。
	 * @author Yangcl
	 * @date 2018年11月19日 上午11:27:53 
	 * @version 1.0.0.1
	 */
	public String powerGet(String key) {
		key = baseKey + key;
		return this.get(key);
	}

	/**
	 * @description: 设置一级缓存|将字符串值 value 关联到 key
	 *
	 * @param key
	 * @param value
	 * @author Yangcl
	 * @date 2018年11月19日 上午11:29:22 
	 * @version 1.0.0.1
	 */
	public void powerSet(String key, String value) {
		key = baseKey + key;
		this.set(key, value);
	}
	
	
	public void powerSet(String key, String value , long timeout) {
		key = baseKey + key;
		this.set(key, value);
	}

	


	@Override
	public Boolean setWithDefExpire(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeyTimeout(String key, Long expire) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer appendStringInEnd(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean batchInsert(List<RedisEntity> list, long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAndSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long size(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long increment(String key, Long amount, long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long incrementTimeout(String key, Long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long del(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long deleteMore(String... keys) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean batchDeleteByPrefix(String prefix) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String hget(String key, String field) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> hgetAll(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hset(String key, String field, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hset(String key, String field, String value, long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetAll(String key, Map<String, String> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hsetAll(String key, Map<String, String> map, long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long hdel(String key, String... fields) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean hkeyExist(String key, String hashKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long addSet(String key, String... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long addSet(String key, long expire, String... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> sget(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setLength(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isInSet(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long setElementRemove(String key, String... members) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addZset(String key, String value, double sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean addZset(String key, String value, double sort, long expire) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zsetRangeIndex(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> zsetRangeByScore(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zsetCount(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zsetScore(String key, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zsetSize(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zsetRemove(String key, String... values) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zsetRemoveRangeByScor(String key, double min, double max) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long zsetRemoveRange(String key, long start, long end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getExpire(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double zincrby(String key, String member, double amount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean close() {
		// TODO Auto-generated method stub
		return null;
	}
	
	


}































package com.matrix.cache.servletContext.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;

/**
 * @description: 针对缓存提供基本的增删改查操作 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年4月12日 下午6:31:56 
 * @version 1.0.0
 */
public class ContextFactory implements ICacheFactory{
	
	private String baseKey = "";
	private String load = "";  // ILoadCache		
	private ServletContext context = ContextCore.getInstance().getApplication();

	public ContextFactory(String baseKey , String load) { 
		if(StringUtils.isBlank(load)) {
			load = "";
		}
		this.baseKey = baseKey;
		this.load = "com.matrix.load." + load;
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
	public String get(String key){
		String value = (String) context.getAttribute(baseKey + key);
		if(StringUtils.isBlank(value)) {
			synchronized (CacheLaunch.class) {
				value = (String) context.getAttribute(baseKey + key);
				if(StringUtils.isBlank(value)) {
					if(this.load.length() == 16) {
						return "";
					}
					try {
						Class<?> clazz = Class.forName(load);   
						if (clazz != null && clazz.getDeclaredMethods() != null){
							@SuppressWarnings("unchecked")
							ILoadCache cache = (ILoadCache) clazz.newInstance();
							return  cache.load(key , "").toString();
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
	
	public void set(String key , String value){
		context.setAttribute(baseKey + key, value);
	}
	
	public Boolean setnx(final String key, final String requestFlag, final Long timeout, final String nxxx , final String expx) {
		return true;
	}
	
	public void del(String key){
		context.removeAttribute(baseKey + key);
	}

	@Override
	public void batchDel(String key) {

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
		// TODO Auto-generated method stub
		return null;
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
	}
	
	public void powerSet(String key, String value , long timeout) {
		
	}
	
	@Override
	public void set(String key, String value, long timeout) {
	}

	@Override
	public void setDefalutTimeout(String key, String value) {
	}

	/**
	 * @description:自定义 redis key 设置超时时间
	 *
	 * @param key
	 * @param expireTime
	 * 
	 * @author Sjh
	 * @date 2019/4/10 16:29
	 * @version 1.0.1
	 */
	public void setKeyTimeout(String key, Long expireTime) {

	}

	@Override
	public Integer appendStringInEnd(String key, String value) {
		return null;
	}

	@Override
	public String getAndSet(String key, String value) {
		return null;
	}

	@Override
	public Long size(String key) {
		return null;
	}

	@Override
	public Long increment(String key, Long delta, long timeout) {
		return null;
	}

	@Override
	public Object hget(String key, String field) {
		return null;
	}

	@Override
	public Map<Object, Object> hgetAll(String key) {
		return null;
	}

	/**
	 * @description: 将哈希表key中的域 field 的值设为value。如果 key 不存在，
	 * 		一个新的哈希表被创建并进行 HSET 操作。如果域 field 已经存在于哈希表中，旧值将被覆盖。
	 *
	 * 例如：
	 *			redisTemplate.opsForHash().put("hashValue","map1","map1-1");
	 *			redisTemplate.opsForHash().put("hashValue","map2","map2-2");
	 *
	 * @param key
	 * @param field
	 * @param value
	 * @param expireTime
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:26:13
	 * @version 1.0.0.1
	 */
	@Override
	public void hset(String key, String field, String value, Long expireTime) {

	}

	/**
	 * @description: 以map集合的形式添加键值对。
	 * 例如：
	Map newMap = new HashMap();
	newMap.put("map3","map3-3");
	newMap.put("map5","map5-5");
	redisTemplate.opsForHash().putAll("hashValue",newMap);
	Map map = redisTemplate.opsForHash().entries("hashValue");
	 *
	 * @param key
	 * @param map
	 * @param expireTime
	 * @author Yangcl
	 * @date 2018年9月18日 下午8:34:31
	 * @version 1.0.0.1
	 */
	@Override
	public void hsetAll(String key, Map<String, Object> map, Long expireTime) {

	}


	@Override
	public Long hdel(String key, Object... field) {
		return null;
	}

	@Override
	public Boolean hkeyExist(String key, Object hashKey) {
		return null;
	}

	@Override
	public Long addSet(String key, String... values) {
		return null;
	}

	/**
	 * @description: 获取变量中的值。
	 *
	 * @param key
	 * @param field
	 * @author Yangcl
	 * @date 2018年9月19日 上午10:27:10
	 * @version 1.0.0.1
	 */
	@Override
	public Set<?> sget(String key) {
		return null;
	}


	@Override
	public Long setLength(String key) {
		return null;
	}

	@Override
	public String setRandomMember(String key) {
		return null;
	}

	@Override
	public List<String> setRandomMembers(String key, long count) {
		return null;
	}

	@Override
	public Boolean isInSet(String key, String value) {
		return null;
	}

	@Override
	public Long setElementRemove(String key, Object... objects) {
		return null;
	}

	@Override
	public Boolean addZset(String key, String value, double sort) {
		return null;
	}

	@Override
	public Set<String> zsetRangeIndex(String key, long start, long end) {
		return null;
	}

	@Override
	public Set<String> zsetRangeByScore(String key, double min, double max) {
		return null;
	}

	@Override
	public Long zsetCount(String key, double min, double max) {
		return null;
	}

	@Override
	public Double zsetScore(String key, String value) {
		
		return null;
	}

	@Override
	public Long zsetSize(String key) {
		
		return null;
	}

	@Override
	public Long zsetRemove(String key, Object... values) {
		
		return null;
	}

	@Override
	public Long zsetRemoveRangeByScor(String key, double min, double max) {
		
		return null;
	}

	@Override
	public Long zsetRemoveRange(String key, long start, long end) {
		
		return null;
	}

	public Long getExpire(String key) {
		return null;
	}



	

	
	
	
}



























package com.matrix.cache.servletContext.core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.map.MDataMap;

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
							ILoadCache cache = (ILoadCache) clazz.newInstance();
							return cache.load(key , "");
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
	
	public void del(String key){
		context.removeAttribute(baseKey + key);
	}

	@Override
	public void set(String key, String value, long timeout) {
	}

	@Override
	public void setDefalutTimeout(String key, String value) {
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
	public Long increment(String key, Long delta) {
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

	@Override
	public void hset(String key, String field, String value) {
	}

	@Override
	public void hsetAll(String key, Map<String, Object> map) {
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

	@Override
	public Set<String> sget(String key) {
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



	

	
	
	
}



























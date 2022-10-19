package com.matrix.system.cache;

import java.net.URL;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import net.sf.ehcache.Element;

/**
 * @description: 系统一级缓存核心支持类|使用Ehcache
 * 
 *	更多内容请参考：
 *		https://blog.csdn.net/vbirdbest/article/details/72763048
 *		https://www.cnblogs.com/zhangzhen894095789/p/6525845.html
 *
 * @author Yangcl
 * @date 2018年11月2日 下午8:18:09 
 * @version 1.0.0.1
 */
@Slf4j
public class PowerCache{
	
	private CacheManager cacheManager = null;
	
	private PowerCache(){
		if (cacheManager == null) {
			URL url = this.getClass().getResource("/META-INF/ehcache/ehcache.xml");  
			cacheManager = CacheManager.create(url);
			log.info("System Power Cache Instantiated Finish !");
		}
	}
	private static class LazyHolder{
		private static final PowerCache INSTANCE = new PowerCache();
	}
	public static final PowerCache getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	public CacheManager cacheManager() {
		return cacheManager;
	}
	
	/**
	 * @description: 获取一级缓存中的Ehcache分类对象|在项目生命周期中，cacheManager对象唯一，不会出现多个实例的情况。
	 *
	 *		CacheManager：是缓存管理器，可以通过单例或者多例的方式创建，也是Ehcache的入口类。
	 *		Cache：每个CacheManager可以管理多个Cache，每个Cache可以采用hash的方式管理多个Element。
	 *		Element：用于存放真正缓存内容的。
	 *		具体结构请参阅项目中：document/Ehcache-缓存数据结构图.png
	 *
	 * @param cacheName Cache对象的名称
	 * @author Yangcl
	 * @date 2018年11月2日 下午8:29:42 
	 * @version 1.0.0.1
	 */
	public Cache getCache(String cacheName) {
		if (cacheManager.cacheExists(cacheName)) {
			return cacheManager.getCache(cacheName);
		}
		return this.addPowerCache(cacheName);
	}
	
	/**
	 * @description: 添加cacheManager对象下的一个Cache分类
	 *
	 * @param name
	 * @author Yangcl
	 * @date 2018年11月2日 下午8:32:15 
	 * @version 1.0.0.1
	 */
	public synchronized Cache addPowerCache(String name) {
		if (cacheManager.cacheExists(name)) {
			return getCache(name);
		} else {
			CacheConfiguration config = new CacheConfiguration();
			config.setName(name);
			config.setEternal(true);
			config.setTimeToIdleSeconds(0);
			config.setMaxEntriesLocalHeap(99999999);
			
			Cache memoryOnlyCache = new Cache(config);
			cacheManager.addCache(memoryOnlyCache);
			return memoryOnlyCache;
		}
	}
	
	/**
	 * @description: 重置一个缓存值
	 *
	 * @param cacheName
	 * @param key
	 * @param value 
	 * @author Yangcl
	 * @date 2018年11月18日 下午10:40:18 
	 * @version 1.0.0.1
	 */
	public void reset(String cacheName, String key, Object value) {
		if(this.containsKey(cacheName, key)) {
			this.remove(cacheName, key);
		}
		this.put(cacheName, key, value);
	}
	
	/**
	 * @description: 判断当前Cache是否存在指定Key
	 *
	 * @param cacheName
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月18日 下午10:42:19 
	 * @version 1.0.0.1
	 */
	public boolean containsKey(String cacheName , String key) {
		Cache cache = cacheManager.getCache(cacheName);  
		if(cache == null) {
			return false;
		}
		return cache.isKeyInCache(key);
	}
	
	/**
	 * @description: 查找key所对应的value值
	 *
	 * 	PowerCache.getInstance().find("PropConfig" ,"matrix-web.model"); 
	 * 
	 * @param cacheName
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月20日 下午6:35:43 
	 * @version 1.0.0.1
	 */
	public Object find(String cacheName , String key) {
		if(!this.containsKey(cacheName, key)) {
			return null;
		}
		return cacheManager.getCache(cacheName).get(key).getObjectValue();
	}
	
	/**
	 * @description: 在指定Cache对象中添加一条记录
	 *
	 * @param cacheName 定位cacheManager中的Cache对象
	 * @param key		just a key
	 * @param value 	set a value 
	 * @author Yangcl
	 * @date 2018年11月3日 下午10:31:32 
	 * @version 1.0.0.1
	 */
	public boolean put(String cacheName, String key, Object value) {  
        Cache cache = cacheManager.getCache(cacheName);  
        if(cache == null) {
        	return false;
        }
        Element element = new Element(key, value);  
        cache.put(element);  
        return true;
    }  
	
	/**
	 * @description: 强制在指定Cache对象中添加一条记录
	 *
	 * @param cacheName 定位cacheManager中的Cache对象
	 * @param key		just a key
	 * @param value 	set a value 
	 * @author Yangcl
	 * @date 2018年11月3日 下午10:31:32 
	 * @version 1.0.0.1
	 */
	public boolean compelPut(String cacheName, String key, Object value) {  
        Cache cache = this.getCache(cacheName);  
        Element element = new Element(key, value);  
        cache.put(element);  
        return true;
    }
	
  
	/**
	 * @description: 移除一个指定Cache对象下的缓存
	 *
	 * @param cacheName
	 * @param key 
	 * @author Yangcl
	 * @date 2018年11月3日 下午10:34:29 
	 * @version 1.0.0.1
	 */
    public boolean remove(String cacheName, String key) {  
        Cache cache = cacheManager.getCache(cacheName);  
        if(cache == null) {
        	return false;
        }
        return cache.remove(key); 
    } 
}



















































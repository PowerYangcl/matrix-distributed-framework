package com.matrix.cache;

import com.matrix.base.BaseClass;

import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * @deprecated
 * @description: ehcache 缓存定义类|RootCatch.java 和 RootCustomCache.java会分别继承该类
 * 
 * @author zht
 * @copy_by Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午6:08:15 
 * @version 1.0.0
 */
public class CacheDefine extends BaseClass {

	private static Logger logger = Logger.getLogger(CacheDefine.class);
	
	// cache名称如果以该值开始 则标记该cache继承自rootcache
	private final static String CACHE_TYPE_NO_ETERNAL = "rootcache:";
	// 自定义cache 该cache自由设置配置 清除缓存时不清除该类cache
	private final static String CACHE_TYPE_CUSTOM = "customcache:";
	
	private static CacheManager cacheManager;

 
	/**
	 * @description: 获取ecatch配置定义|该配置文件定义在src/main/resources/META-INF/ehcache/路径下
	 * 
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:13:09 
	 * @version 1.0.0.1
	 */
	public CacheDefine() {
		if (cacheManager == null) {
			URL url = this.getClass().getResource("/META-INF/ehcache/ehcache.xml");  
			cacheManager = CacheManager.create(url);
		}
	}

	/**
	 * @description: 添加缓存(配置文件类型的缓存)|缓存类型：rootcache|RootCatch.java会继承该类，并使用这个方法添加缓存
	 * 		清除缓存时，以rootcache开头的所有缓存都会被清除。
	 * @alias inCache 
	 * 
	 * @param cacheName 缓存后缀名称
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:17:50 
	 * @version 1.0.0.1
	 */
	public synchronized Cache addRootCache(String cacheName) {
		cacheName = CACHE_TYPE_NO_ETERNAL + cacheName;
		System.out.println("Ehcache 开始初始化 名称为：" + cacheName + " @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		if (cacheManager.cacheExists(cacheName)) {
			System.out.println("Ehcache 开始初始化 名称为：" + cacheName + "  已经存在@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			return getCache(cacheName);
		} else {
			CacheConfiguration cacheConfiguration = new CacheConfiguration();
			cacheConfiguration.setName(cacheName);
			cacheConfiguration.setEternal(true);
			cacheConfiguration.setTimeToIdleSeconds(0);
			cacheConfiguration.setMaxEntriesLocalHeap(99999999);
			Cache memoryOnlyCache = new Cache(cacheConfiguration);
			cacheManager.addCache(memoryOnlyCache);
			return memoryOnlyCache;
		}
	}

 
	/**
	 * @description: 添加自定义的缓存(非配置文件类型的缓存)|缓存类型：customcache|RootCustomCache.java会继承该类，并使用这个方法添加缓存
	 * 			该cache自由设置配置 清除缓存时不清除该类cache
	 * @alias inCustomCache 
	 * 
	 * @param cacheName
	 * @param cacheConfiguration
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:25:56 
	 * @version 1.0.0.1
	 */
	public synchronized Cache addCustomCache(String cacheName , CacheConfiguration cacheConfiguration) {
		cacheName = CACHE_TYPE_CUSTOM + cacheName;
		if (cacheManager.cacheExists(cacheName)) {
			return getCache(cacheName);
		} else {
			Cache memoryOnlyCache = new Cache(cacheConfiguration);
			cacheManager.addCache(memoryOnlyCache);
			return memoryOnlyCache;
		}
	}

	/**
	 * @description:  获取缓存
	 * @alias upCache
	 * 
	 * @param name cache name
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:22:01 
	 * @version 1.0.0.1
	 */
	public Cache getCache(String name) {
		return cacheManager.getCache(name);
	}

	
	/**
	 * @description: 清除所有继承自rootcache的缓存
	 * 
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:31:08 
	 * @version 1.0.0.1
	 */
	public synchronized void removeAllCache() {
		for (String sKey : cacheManager.getCacheNames()) {
			if (StringUtils.startsWith(sKey, CACHE_TYPE_NO_ETERNAL)) {
				Cache cache = getCache(sKey);
				this.getLogger(logger).logInfo(0, "CacheDefine.java 开始清除缓存：" + cache.getName());
				cache.removeAll();
			}
		}
	}
	
	

}

























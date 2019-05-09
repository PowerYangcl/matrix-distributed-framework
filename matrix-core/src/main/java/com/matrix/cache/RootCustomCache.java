package com.matrix.cache;

import java.util.List;

import com.matrix.base.BaseClass;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;

/**
 * @deprecated
 * @descriptions 缓存基类|非配置文件类型的缓存均需继承此基类
 *
 * @author zht
 * @copy_by Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月22日 下午10:36:55
 * @version 1.0.1
 */
public class RootCustomCache<K, V> extends BaseClass {

	private Cache cache;

	/**
	 * 构造函数
	 */
	public RootCustomCache() {
		CacheDefine cDefine = new CacheDefine();
		String sCacheName = this.getClass().getName();
		CacheConfiguration cacheConfiguration = new CacheConfiguration();
		cacheConfiguration.setName(sCacheName);
		// 设置最大数量
		cacheConfiguration.setMaxEntriesLocalHeap(9999999);
		// 设置最长存活时间
		cacheConfiguration.setTimeToIdleSeconds(6000);
		cacheConfiguration.setMemoryStoreEvictionPolicy("FIFO");
		cache = cDefine.addCustomCache(sCacheName, cacheConfiguration);
	}

	public void inElement(K k, V v) {
		cache.put(new Element(k, v));
	}

	
	public void inElement(Element element){
		cache.put(element);
	}
	
	//alias upKeys
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {
		return cache.getKeys();
	}

	/**
	 * alias upValueAndRemove
	 * @param k
	 * @return
	 */
	public V getValueAndRemove(K k) {
		Object oReturnObject = null;
		if (cache.isKeyInCache(k)) {
			Element eCachElement = cache.get(k);
			if (eCachElement != null) {
				oReturnObject = eCachElement.getObjectValue();
			}
		}

		cache.remove(k);
		return (V) oReturnObject;
	}
	
	
	
	

	/**
	 * alias upValue
	 * 获取指定key对应的值
	 * 
	 * @param k
	 * @return
	 */
	public V getValue(K k) {
		Object oReturnObject = null;
		if (cache.isKeyInCache(k)) {
			Element eCachElement = cache.get(k);
			if (eCachElement != null) {
				oReturnObject = eCachElement.getObjectValue();
			}
		}
		return (V) oReturnObject;
	}
}

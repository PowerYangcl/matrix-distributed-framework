package com.matrix.base;

import java.util.List;

import com.matrix.base.interfaces.IBaseCache;
import com.matrix.system.cache.PowerCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @description: 系统一级缓存抽象实现类|所有操作一级缓存Ehcache的类均需要继承此类
 *
 * @author Yangcl
 * @date 2018年11月3日 下午4:02:59 
 * @version 1.0.0.1
 */
public abstract class BaseEhcache<K , V> implements IBaseCache<K>{

	private Cache cache;
	
	public BaseEhcache(String cacheName){
		cache = PowerCache.getInstance().addPowerCache(cacheName);
	}
	
	/**
	 * @description: 一级缓存没有找到则从二级缓存查找数据|此处也可以不返回任何值。
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月4日 下午10:20:46 
	 * @version 1.0.0.1
	 */
	public abstract V find(K key);
	
	/**
	 * @description: 获取缓存的值
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月3日 下午4:40:02 
	 * @version 1.0.0.1
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K key) {
		if(!this.containsKey(key)) {
			this.refresh(key);
		}
		if(this.containsKey(key)) {
			return (V) cache.get(key).getObjectValue();
		}
		return this.find(key);
	}
	
	/**
	 * @description: 验证是否已经刷新
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2018年11月16日 下午4:48:38 
	 * @version 1.0.0.1
	 */
	public String getRefreshFlag(K key) {
		if(this.containsKey(key)) {
			return (String) cache.get(key).getObjectValue();
		}
		return "";
	}
	
	
	
	/**
	 * @description: 添加缓存
	 *
	 * @param k
	 * @param v 
	 * @author Yangcl
	 * @date 2018年11月3日 下午4:18:31 
	 * @version 1.0.0.1
	 */
	public void addElement(K k, V v) {
		if(this.containsKey(k)) {
			return ;
		}
		cache.put(new Element(k, v));
	}
	
	/**
	 * @description: 重置一个一级缓存
	 *		
	 * @param k
	 * @param v 
	 * @author Yangcl
	 * @date 2018年11月15日 下午18:26:52
	 * @version 1.0.0.1
	 */
	public void resetElement(K k , V v) {
		this.removeByKey(k);
		cache.put(new Element(k, v));
	}
	
	
	/**
	 * @description: 判断是否存在
	 * 
	 * @param k
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:49:34 
	 * @version 1.0.0.1
	 */
	public boolean containsKey(K k) {
		return cache.isKeyInCache(k);
	}

	/**
	 * @description: 获取所有缓存的Key
	 * 
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:49:56 
	 * @version 1.0.0.1
	 */
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {
		return cache.getKeys();
	}
	
	/**
	 * @description: 清空Cache对象下的所有缓存
	 * 
	 * @author Yangcl
	 * @date 2018年11月3日 下午10:59:59 
	 * @version 1.0.0.1
	 */
	public void removeAll() {
		cache.removeAll();
	}
	
	/**
	 * @description: 移除一个指定Cache对象下的缓存
	 *
	 * @param key 
	 * @author Yangcl
	 * @date 2018年11月3日 下午10:34:29 
	 * @version 1.0.0.1
	 */
	public void removeByKey(K k) {
		if(this.containsKey(k)) {
			cache.remove(k);
		}
	}
}

























































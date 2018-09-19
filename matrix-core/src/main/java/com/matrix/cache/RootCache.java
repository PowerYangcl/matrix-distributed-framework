package com.matrix.cache;

import java.util.List;

import org.apache.log4j.Logger;

import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

 
/**
 * @description: 缓存基类|配置文件类型的缓存(****.properties)|ecache涉及缓存的操作均需继承此基类
 * 
 * @author zht
 * @copy  Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午6:04:51 
 * @version 1.0.0
 */
public abstract class RootCache<K, V> extends BaseClass implements IBaseCache {

	private static Logger logger = Logger.getLogger(RootCache.class);
	
	private Cache cache;

	/**
	 * @description: 随构造函数加载
	 * 
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:45:12 
	 * @version 1.0.0.1
	 */
	public RootCache() {
		CacheDefine cDefine = new CacheDefine();
		String classNameString = this.getClass().getName();
		cache = cDefine.addRootCache(classNameString);
	}
	
	
	/**
	 * @description: 获取一个被子类设置好的缓存内容|默认返回null<br>
	 * 		实现该类的子类如果不需要设置任何缓存，则可以默认返回null。
	 * 		这个被设置的缓存内容可以是一个Java对象，也可以是一个List 或者 Map
	 * 		 
	 * 		
	 * @alias upOne
	 * @alias getOne
	 * 
	 * @param k
	 * @return 默认返回null
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:51:04 
	 * @version 1.0.0.1
	 */
	public abstract V getOneSetCatch(K k); 

	/**
	 * @description: 添加缓存
	 * 
	 * @param k
	 * @param v
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:46:03 
	 * @version 1.0.0.1
	 */
	public void addElement(K k, V v) {
		cache.put(new Element(k, v));
	}

	/**
	 * @description: 判断是否存在
	 * 
	 * @param k
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:49:34 
	 * @version 1.0.0.1
	 */
	public boolean containsKey(K k) {
		return cache.isKeyInCache(k);
	}

	/**
	 * @description: 获取所有缓存的Key
	 * @alias upKeys
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:49:56 
	 * @version 1.0.0.1
	 */
	@SuppressWarnings("unchecked")
	public List<K> getKeys() {
		return cache.getKeys();
	}

 
	/**
	 * @description: 获取缓存的值
	 * @alias upValue
	 * 
	 * @param k
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午7:18:18 
	 * @version 1.0.0.1
	 */
	@SuppressWarnings("unchecked")
	public V getValue(K k) {
		if (!containsKey(k)) {
			synchronized (this) {
//				getLogger().logInfo(0, "RootCache.java正在重新加载缓存信息：" + k.toString());
//				System.out.println("RootCache.java正在重新加载缓存信息：" + k.toString());
				this.getLogger(logger).logInfo("RootCache.java正在重新加载缓存信息：" + k.toString()); 
				refresh();
			}
		}

		if (containsKey(k)) {
			return (V) cache.get(k).getObjectValue();
		} else {
			V v = null;
			synchronized (this) {
				if (containsKey(k)) {
					v = (V) cache.get(k).getObjectValue();
				} else {
					v = getOneSetCatch(k);  // 获取一个子类设置的缓存内容
					this.getLogger(logger).logInfo(0, this.getClass().getName() + " 开始设置自定义缓存 " + k.toString() + "  RootCatch.java 执行中");
//					System.out.println(this.getClass().getName() + " 开始设置自定义缓存 " + k.toString() + "  RootCatch.java 执行中");
					if (v != null && !containsKey(k)) {
						addElement(k, v);
					}
					else if(v==null){
						this.getLogger(logger).logInfo(0 , this.getClass().getName() + " 未成功加载自定义缓存内容，其缓存定义可能为空 "+k.toString()  + "  RootCatch.java 执行中");
//						System.out.println(this.getClass().getName() + " 未成功加载自定义缓存内容，其缓存定义可能为空 "+k.toString()  + "  RootCatch.java 执行中");
					}
				}
			}
			return v;
		}
	}

	public void removeAll() {
		cache.removeAll();
	}
	
	public void removeByKey(K k) {
		synchronized (this) {
			cache.remove(k);
		}
	}

}

























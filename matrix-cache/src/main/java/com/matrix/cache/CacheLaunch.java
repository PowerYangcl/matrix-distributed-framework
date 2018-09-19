package com.matrix.cache;


import com.matrix.base.BaseClass;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.redis.launch.RedisLaunch;
import com.matrix.cache.servletContext.launch.ContextLaunch;

/**
 * @description: 缓存对外服务入口|单例|隔离Redis或其他缓存
 * 	调用方式如下：IBaseLaunch launch = CacheLaunch.getInstance().Launch();
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年4月12日 下午6:51:17 
 * @version 1.0.0
 */
public class CacheLaunch extends BaseClass{ 
 
	private IBaseLaunch<ICacheFactory> launch; 
	
	private CacheLaunch() {
		String cacheType = this.getConfig("matrix-cache.cache_launch_type");
		if(cacheType.equals("redis")){ 
			launch = new RedisLaunch();
		}else if(cacheType.equals("context")){ 
			launch = new ContextLaunch();
		}
	} 
	private static class LazyHolder {
		private static final CacheLaunch INSTANCE = new CacheLaunch();
	}
	public static final CacheLaunch getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	public IBaseLaunch<ICacheFactory> Launch(){ 
		return launch;
	}
}





















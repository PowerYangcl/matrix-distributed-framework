package com.matrix.cache.power;

import org.apache.commons.lang3.StringUtils;

import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.power.core.PowerFactory;
import com.matrix.cache.redis.core.RedisFactory;

/**
 * @description: 系统一级缓存核心工具类
 *
 * @author Yangcl
 * @date 2018年11月18日 下午8:39:48 
 * @version 1.0.0.1
 */
public class PowerLaunch implements IBaseLaunch<ICacheFactory>  {

	public PowerFactory loadDictCache(CachePrefix enum_, String load) {
		return new PowerFactory("xd-" + enum_.toString() + "-" , load);
	}

	@Override
	public ICacheFactory loadServiceCache(String prefix, String load) {
		if(StringUtils.isBlank(prefix)) {
			return null;
		}
		return new RedisFactory("xs-" + prefix + "-" , load);
	}

	@Override
	public ICacheFactory loadDictCache(String prefix, String load) {
		if(StringUtils.isBlank(prefix)) {
			return null;
		}
		return new RedisFactory("xs-" + prefix + "-" , load);
	}

}

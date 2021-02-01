package com.matrix.cache.power;

import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.cache.power.core.PowerFactory;

/**
 * @description: 系统一级缓存核心工具类
 *
 * @author Yangcl
 * @date 2018年11月18日 下午8:39:48 
 * @version 1.0.0.1
 */
public class PowerLaunch implements IBaseLaunch<ICacheFactory>  {

	public PowerFactory loadServiceCache(SCacheEnum enum_, String load) {
		return new PowerFactory("xs-" + enum_.toString() + "-" , load);
	}

	public PowerFactory loadDictCache(DCacheEnum enum_, String load) {
		return new PowerFactory("xd-" + enum_.toString() + "-" , load);
	}

}

package com.matrix.system.init;

import com.matrix.base.BaseInit;
import com.matrix.system.DictionaryCacheSupport;

/**
 * @descriptions 初始化数据库中的关键信息到缓存中|涉及到数据字典功能的表数据会被该类执行
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年12月11日 下午4:17:17
 * @version 1.0.1
 */
public class DictionaryTableCacheInit extends BaseInit {

	public boolean onInit() {
		return new DictionaryCacheSupport().supportInit();
	}

	public boolean onDestory() {
		return new DictionaryCacheSupport().supportDelete();
	}

}

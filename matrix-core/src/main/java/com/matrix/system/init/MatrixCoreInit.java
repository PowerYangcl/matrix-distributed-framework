package com.matrix.system.init;

import org.apache.commons.lang.StringUtils;

import com.matrix.cache.CacheDefine;
import com.matrix.system.cache.TopConst;

/**
 * @descriptions matrix-core 初始化类
 * @alias InitZapcom
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月15日 下午9:41:57
 * @version 1.0.1
 */
public class MatrixCoreInit extends RootInit {

	public boolean onInit() {
		return initVersion();
	}

	/**
	 * @descriptions 初始化版本号
	 *
	 * @return
	 * @date 2016年11月15日 下午9:42:48
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public boolean initVersion() {
		// 定义系统加载的模型类型  不同生产环境下该变量应该为不一样 开发环境为dev 测试环境为beta 候选版本为rc 生产版本为idc
		TopConst.CONST_CURRENT_MODEL = getConfig("matrix-core.model");
		String version = getConfig("matrix-core.version");
		String custVersion = getConfig("matrix-core.version_" + TopConst.CONST_CURRENT_MODEL);
		if (StringUtils.isNotBlank(custVersion)) {
			version = custVersion;
		}
		TopConst.CONST_CURRENT_VERSION = version;
		TopConst.CONST_LOG_TYPE = getConfig("matrix-core.log_type");   // 统一日志的操作类型
		TopConst.CONST_LOG_ADDRESS = getConfig("matrix-core.log_address");  // 统一日志的传送地址
		return true;
	}

	@Override
	public boolean onDestory() {
		// 清除所有缓存
		new CacheDefine().removeAllCache();
		return true;
	}

}




















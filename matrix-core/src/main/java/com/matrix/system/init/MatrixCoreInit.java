package com.matrix.system.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;
import com.matrix.base.BaseInit;
import com.matrix.base.BaseLog;
import com.matrix.system.cache.PowerCache;
import com.matrix.system.cache.PropConfig;

/**
 * @descriptions 差异化配置信息验证
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月15日 下午9:41:57
 * @version 1.0.1
 */
public class MatrixCoreInit extends BaseInit {

	public boolean onInit() {
		return this.deployEnvValidate();
	}

	/**
	 * @description: 开始重置Dubbo服务项目中的差异化配置信息
	 *
	 * @return 
	 * @author Yangcl
	 * @date 2018年11月16日 下午5:58:55 
	 * @version 1.0.0.1
	 */
	private boolean deployEnvValidate(){
		if(StringUtils.isNotBlank(this.getConfig("matrix-web.model"))) {
			BaseLog.getInstance().sysoutInfo("-------------------------------------------Web Project Properties File Init Finished ! ! ! ! ! ! ! " , this.getClass());
			return true;
		}
		
		String path = this.getClass().getClassLoader().getResource("").getPath();
		BaseLog.getInstance().sysoutInfo("-------------------------------------------Current System Running Path Is : " + path , this.getClass());
		if(!StringUtils.contains(path, "properties")) {
			path += "properties";
		}
		File file = new File(path);
		if(!file.exists()) {
			BaseLog.getInstance().sysoutInfo("-------------------------------------------不存在额外的差异化配置文件 " , this.getClass());
			return true;
		}
		
		Properties properties = new Properties();  // 使用InPutStream流读取properties文件
		BufferedReader bufferedReader;
		try {
			bufferedReader = new BufferedReader(new FileReader(path + "/dubbo.properties"));
			properties.load(bufferedReader);  // 获取key对应的value值
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(StringUtils.isBlank(properties.getProperty("dubbo.application.model"))) {
			return true;
		}
		BaseLog.getInstance().sysoutInfo("-------------------------------------------Dubbo服务开始初始化差异化配置! " , this.getClass());
		BaseLog.getInstance().sysoutInfo("-------------------------------------------系统初始运行环境：" + this.getConfig("matrix-core.model")  , this.getClass());
		if(this.getConfig("matrix-core.build_type").equals("jenkins")) {
			// 根据部署结果重置项目版本信息，此时将覆盖config.matrix-core.properties文件中的model信息
			PowerCache.getInstance().reset("PropConfig", "matrix-core.model", properties.getProperty("dubbo.application.model")); 
			BaseLog.getInstance().sysoutInfo("-------------------------------------------系统重置运行环境：" + this.getConfig("matrix-core.model")  , this.getClass());
		}
		PowerCache.getInstance().reset("PropConfig", "matrix-core.zookeeper_host", properties.getProperty("dubbo.registry.address")); 
		PowerCache.getInstance().reset("PropConfig", "matrix-core.dubbo_application_name", properties.getProperty("dubbo.application.name")); 
		PowerCache.getInstance().reset("PropConfig", "matrix-core.dubbo_application_owner", properties.getProperty("dubbo.application.owner")); 
		BaseLog.getInstance().sysoutInfo("-------------------------------------------Zookeepper重置地址：" + this.getConfig("matrix-core.zookeeper_host")  , this.getClass());
		return true;
	}
	
	
	

	public boolean onDestory() {
		return true;
	}

}




















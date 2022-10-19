package com.matrix.system.init;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseInit;
import com.matrix.system.cache.PowerCache;

import lombok.extern.slf4j.Slf4j;

/**
 * @descriptions 差异化配置信息验证
 *
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月15日 下午9:41:57
 * @version 1.0.1
 */
@Slf4j
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
			log.info("-------------------------------------------Web Project Properties File Init Finished ! ! ! ! ! ! ! ");
			return true;
		}
		
		String path = this.getClass().getClassLoader().getResource("").getPath();
		log.info("-------------------------------------------Current System Running Path Is : " + path);
		if(!StringUtils.contains(path, "properties")) {
			path += "properties";
		}
		File file = new File(path);
		if(!file.exists()) {
			log.info("-------------------------------------------不存在额外的差异化配置文件 ");
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
		log.info("-------------------------------------------Dubbo服务开始初始化差异化配置! ");
		log.info("-------------------------------------------系统初始运行环境：" + this.getConfig("matrix-core.model"));
		if(this.getConfig("matrix-core.build_type").equals("jenkins")) {
			// 根据部署结果重置项目版本信息，此时将覆盖config.matrix-core.properties文件中的model信息
			PowerCache.getInstance().reset("PropConfig", "matrix-core.model", properties.getProperty("dubbo.application.model")); 
			log.info("-------------------------------------------系统重置运行环境：" + this.getConfig("matrix-core.model"));
		}
		PowerCache.getInstance().reset("PropConfig", "matrix-core.zookeeper_host", properties.getProperty("dubbo.registry.address")); 
		PowerCache.getInstance().reset("PropConfig", "matrix-core.dubbo_application_name", properties.getProperty("dubbo.application.name")); 
		PowerCache.getInstance().reset("PropConfig", "matrix-core.dubbo_application_owner", properties.getProperty("dubbo.application.owner")); 
		log.info("-------------------------------------------Zookeepper重置地址：" + this.getConfig("matrix-core.zookeeper_host"));
		return true;
	}
	

	public boolean onDestory() {
		return true;
	}

}




















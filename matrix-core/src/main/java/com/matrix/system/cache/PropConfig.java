package com.matrix.system.cache;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import com.matrix.base.BaseEhcache;
import com.matrix.util.IoUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @descriptions 初始化项目配置文件中的【属性配置信息】到ecache中
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl 
 * @date 2016年11月12日 下午6:23:36
 * @version 1.0.1
 */
@Slf4j
public class PropConfig extends BaseEhcache<String, String> {
	
	private PropConfig(){
		super("PropConfig");
	}
	private static class LazyHolder{
		private static final PropConfig INSTANCE = new PropConfig();
	}
	public static final PropConfig getInstance() {
		return LazyHolder.INSTANCE; 
	}

	public synchronized void refresh(String key_) {
		if(this.getRefreshFlag("prop-config").equals("true")) {
			// 未发现的Key：系统配置信息同步已经完成 
			log.info("系统配置信息同步已经完成，未发现的key = " + key_ );
			return;
		}
		this.addElement("prop-config", "true"); // 添加刷新标记
		
		SysWorkDir configDir = new SysWorkDir();
		String tempPath = configDir.getTempDir("config/");
		log.info("开始同步并刷新项目配置文件： " + tempPath);
		IoUtil.getInstance().copyResources("classpath*:META-INF/matrix/config/*.properties" , tempPath , "/matrix/config/");
		
		LoadProperties loadProperties = new LoadProperties();
		// 开始读取配置放入到ecache中
		Map<String,String> map = loadProperties.loadMap(tempPath);
		Map<String,String> reloadMap = new HashMap<String, String>();		// 保存项目中需要差异化特殊配置的信息
		for (String s : map.keySet()) {
			if(StringUtils.contains(s, ".reload_")) {
				reloadMap.put(s, map.get(s));
				continue;
			}
			this.addElement(s, map.get(s));
		}
		if(!reloadMap.isEmpty()) {
			for (String s : reloadMap.keySet()) {
				this.resetElement(s.split(".reload_")[1], reloadMap.get(s));
			}
		}
		
		try {		// 获取本机	ip 同时添加到一级缓存
			InetAddress addr = InetAddress.getLocalHost();
			String ip = addr.getHostAddress().toString();  
			this.addElement("matrix-core.host_ip", ip);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}finally {
			this.addElement("matrix-core.host_ip", "");
		}
		log.info("当前主机地址段：" + this.getValue("matrix-core.host_ip"));
		
		
//		try {
//			ResourceBundle resource = ResourceBundle.getBundle("properties/dubbo");
//			if(this.getValue("matrix-core.build_type").equals("jenkins")) {
//				// 根据部署结果重置项目版本信息，此时将覆盖config.matrix-core.properties文件中的model信息
//				this.resetElement("matrix-core.model", resource.getString("dubbo.application.model"));  
//				log.info("系统运行环境：" + this.getValue("matrix-core.model"));
//			}
//			this.addElement("matrix-core.zookeeper_host", resource.getString("dubbo.registry.address")); 
//			this.addElement("matrix-core.dubbo_application_name", resource.getString("dubbo.application.name")); 
//			this.addElement("matrix-core.dubbo_application_owner", resource.getString("dubbo.application.owner")); 
//			log.info("Zookeepper：" + this.getValue("matrix-core.zookeeper_host"));
//		} catch (Exception ex) {
//			log.error("properties/dubbo.xml文件不存在");
//		}
		
	}


	/**
	 * @descriptions 默认返回空 
	 * 
	 * @date 2016年11月12日 下午6:15:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public String find(String k) {
		return "";
	}
}





























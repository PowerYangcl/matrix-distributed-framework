package com.matrix.system.cache;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.ResourceBundle;

import com.matrix.base.BaseEhcache;
import com.matrix.base.BaseLog;
import com.matrix.util.IoUtil;

/**
 * @descriptions 初始化项目配置文件中的【属性配置信息】到ecache中
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl 
 * @date 2016年11月12日 下午6:23:36
 * @version 1.0.1
 */
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
			BaseLog.getInstance().sysoutInfo("系统配置信息同步已经完成，未发现的key = " + key_ , this.getClass()); 
			return;
		}
		this.addElement("prop-config", "true"); // 添加刷新标记
		
		SysWorkDir configDir = new SysWorkDir();
		String tempPath = configDir.getTempDir("config/");
		BaseLog.getInstance().sysoutInfo("开始同步并刷新项目配置文件： " + tempPath , this.getClass()); 
		IoUtil.getInstance().copyResources("classpath*:META-INF/matrix/config/*.properties" , tempPath , "/matrix/config/");
		
		LoadProperties loadProperties = new LoadProperties();
		// 开始读取配置放入到ecache中
		Map<String,String> map = loadProperties.loadMap(tempPath);
		for (String s : map.keySet()) {	
			this.addElement(s, map.get(s));
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
		
		BaseLog.getInstance().sysoutInfo("-------------------------------------------当前主机地址段：" + this.getValue("matrix-core.host_ip")  , this.getClass());
		
		try {
			ResourceBundle resource = ResourceBundle.getBundle("properties/dubbo");
			if(this.getValue("matrix-core.build_type").equals("jenkins")) {
				// 根据部署结果重置项目版本信息，此时将覆盖config.matrix-core.properties文件中的model信息
				this.resetElement("matrix-core.model", resource.getString("dubbo.application.model"));  
				BaseLog.getInstance().sysoutInfo("-------------------------------------------系统运行环境：" + this.getValue("matrix-core.model")  , this.getClass());
			}
			this.addElement("matrix-core.zookeeper_host", resource.getString("dubbo.registry.address")); 
			this.addElement("matrix-core.dubbo_application_name", resource.getString("dubbo.application.name")); 
			this.addElement("matrix-core.dubbo_application_owner", resource.getString("dubbo.application.owner")); 
			BaseLog.getInstance().sysoutInfo("-------------------------------------------Zookeepper：" + this.getValue("matrix-core.zookeeper_host")  , this.getClass());
		} catch (Exception ex) {
			BaseLog.getInstance().sysoutInfo("-------------------------------------------properties/dubbo.xml文件不存在", this.getClass());
		}
		
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





























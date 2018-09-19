package com.matrix.system.cache;

import org.apache.log4j.Logger;

import com.matrix.cache.RootCache;
import com.matrix.map.MStringMap;
import com.matrix.util.IoUtil;

/**
 * @descriptions 初始化项目配置文件中的【属性配置信息】到ecache中
 * @alias TopConfig
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl 
 * @date 2016年11月12日 下午6:23:36
 * @version 1.0.1
 */
public class PropConfig extends RootCache<String, String> {

	private static Logger logger = Logger.getLogger(PropConfig.class);
	
	public final static PropConfig Instance = new PropConfig();

	public synchronized void refresh() {
		SysWorkDir configDir = new SysWorkDir();
		String tempPath = configDir.getTempDir(TopConst.CONST_TOP_CUSTOM_CONFIG_PATH);
		this.getLogger(logger).logInfo("开始同步并刷新项目配置文件： " + tempPath); 
		IoUtil.getInstance().copyResources("classpath*:META-INF/matrix/config/*.properties" , tempPath , "/matrix/config/");
		
		LoadProperties loadProperties = new LoadProperties();
		{	// 开始读取配置放入到ecache中
			MStringMap mStringMap = loadProperties.loadMap(tempPath);
			for (String s : mStringMap.getKeys()) {
				this.addElement(s, mStringMap.get(s));
			}
		}

		{	// 开始扫描扩展自定义的设置
			String customConfig = configDir.getCustomPath(TopConst.CONST_TOP_CUSTOM_CONFIG_PATH);
			this.getLogger(logger).logDebug(0 , "开始扫描扩展自定义缓存设置：" + customConfig);
			MStringMap customMap = loadProperties.loadMap(customConfig);
			if (customMap.size() == 0) {
				this.getLogger(logger).logInfo("不存在自定义缓存");
			} else {
				for (String s : customMap.getKeys()) {
					this.addElement(s, customMap.get(s));
				}
			}
		}
		
		{	// 开始加载最后本地配置项
			String localConfig = configDir.getLocalConfigPath();
			this.getLogger(logger).logInfo("开始加载本地配置项： " + localConfig); 
			MStringMap customMap = loadProperties.loadMap(localConfig);
			if(customMap.size() == 0){
				this.getLogger(logger).logInfo("不存在本地配置项");
			}else{
				for (String s : customMap.getKeys()) {
					this.addElement(s, customMap.get(s));
				}
				this.getLogger(logger).logInfo("本地配置项加载完成"); 
			}
		}
	}


	/**
	 * @descriptions 默认返回空 
	 * 
	 * @date 2016年11月12日 下午6:15:53
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@Override
	public String getOneSetCatch(String k) {
		return null;
	}
}





























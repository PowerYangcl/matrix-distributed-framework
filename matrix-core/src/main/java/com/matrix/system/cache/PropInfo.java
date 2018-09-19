package com.matrix.system.cache;

import org.apache.log4j.Logger;

import com.matrix.cache.RootCache;
import com.matrix.map.MStringMap;
import com.matrix.util.IoUtil;
 
/**
 * @descriptions 初始化项目配置文件中的【提示消息配置信息】到ecache中|这些信息用于项目国际化
 * @alias TopInfo
 * 
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月12日 下午6:27:35
 * @version 1.0.1
 */
public class PropInfo extends RootCache<Long, String> {
	
	private static Logger logger = Logger.getLogger(PropInfo.class);
	
	public synchronized void refresh() {
		SysWorkDir infoDir = new SysWorkDir();
		String tempConfigPath = infoDir.getTempDir("info/");
		this.getLogger(logger).logInfo("refresh " + tempConfigPath); 
		IoUtil.getInstance().copyResources("classpath*:META-INF/matrix/info/*.properties" , tempConfigPath , "/matrix/info/");
		LoadProperties loadProperties = new LoadProperties();
		MStringMap map = loadProperties.loadMap(tempConfigPath);
		this.getLogger(logger).logInfo("开始加载info配置信息"); 
		for (String s : map.getKeys()) {
			Long lKey=Long.parseLong(s);
			if(containsKey(lKey)){
				this.getLogger(logger).logInfo("警告！key ["+lKey.toString()+"] 不是全局唯一的存在！"); 
			}
			this.addElement(Long.parseLong(s) , map.get(s));
		}
	}
	

	@Override
	public String getOneSetCatch(Long k) {
		return null;
	}
}
































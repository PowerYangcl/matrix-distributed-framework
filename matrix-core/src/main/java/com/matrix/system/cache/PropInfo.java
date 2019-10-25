package com.matrix.system.cache;

import java.util.Map;

import com.matrix.base.BaseEhcache;
import com.matrix.base.BaseLog;
import com.matrix.util.IoUtil;
 
/**
 * @descriptions 初始化项目配置文件中的【提示消息配置信息】到ecache中|这些信息用于项目国际化
 * 
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2016年11月12日 下午6:27:35
 * @version 1.0.1
 */
public class PropInfo extends BaseEhcache<Long, String> {
	
	private PropInfo(){
		super("PropInfo");
	}
	private static class LazyHolder{
		private static final PropInfo INSTANCE = new PropInfo();
	}
	public static final PropInfo getInstance() {
		return LazyHolder.INSTANCE; 
	}

	public synchronized void refresh(Long key_) {
		if(this.getRefreshFlag(99999999999L).equals("true")) {
			BaseLog.getInstance().sysoutInfo("系统消息提示信息同步已经完成，当前系统不包含key = " + key_ , this.getClass()); 
			return;
		}
		this.addElement(99999999999L , "true"); // 添加刷新标记
		
		SysWorkDir infoDir = new SysWorkDir();
		String tempConfigPath = infoDir.getTempDir("info/");
		BaseLog.getInstance().sysoutInfo("开始同步并刷新项目配置文件： " + tempConfigPath , this.getClass()); 
		IoUtil.getInstance().copyResources("classpath*:META-INF/matrix/info/*.properties" , tempConfigPath , "/matrix/info/");
		LoadProperties loadProperties = new LoadProperties();
		Map<String,String> map = loadProperties.loadMap(tempConfigPath);
		BaseLog.getInstance().sysoutInfo("开始加载info配置信息： " + tempConfigPath , this.getClass()); 
		
		for (String s : map.keySet()) {
			Long key=Long.parseLong(s);
			if(super.containsKey(key)){
				BaseLog.getInstance().sysoutInfo("警告！key ["+key.toString()+"] 不是全局唯一的存在！" + tempConfigPath , this.getClass()); 
			}
			this.addElement(Long.parseLong(s) , map.get(s));
		}
	}
	

	@Override
	public String find(Long k) {
		return "";
	}
}
































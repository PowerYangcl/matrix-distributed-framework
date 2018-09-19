package com.matrix.system.init;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.matrix.map.MStringMap;
import com.matrix.system.cache.PropConfig;
import com.matrix.system.cache.PropInfo;
import com.matrix.system.cache.PropVisitor;
import com.matrix.system.cache.SysWorkDir;


/**
 * @descriptions 
 * 	1.删除系统临时目录中的文件
 * 	2.将系统所有垂直子工程的config.xxx.properties和info.xxxx.properties文件从jar包中拷入系统临时目录并加载
 * 	3.依次调用config.xxx.properties中initclass类,完成垂直子工程的初始工作
 *
 *
 * @author 张海涛 
 * @home 北京市朝阳区甜水园儿北里，宝儿爷楼下
 * @date 2016年11月15日 下午8:28:41
 * @version 1.0.1
 */
public class SystemInit extends RootInit {
	
	private static Logger logger = Logger.getLogger(SystemInit.class);
	
	public synchronized boolean onInit() {
		initDelete();
		initProps();
		return initClass();
	}

 
	/**
	 * @descriptions 初始化|删除系统全局配置文件(所有配置文件)所在的临时文件夹 temp
	 *
	 * @date 2016年11月15日 下午8:30:54
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private void initDelete() {
		String dirString = new SysWorkDir().getTempDir("");
		try {
			FileUtils.deleteDirectory(new File(dirString));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

 
	/**
	 * @descriptions 初始化顶级配置|将config和info配置文件刷新到ecache中
	 *
	 * @date 2016年11月15日 下午8:30:54
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private void initProps() {
		rootInitCache(PropConfig.Instance, new PropInfo());
	}

	/**
	 * @descriptions 初始化加载各个类：clazz extends RootInit 
	 *
	 * @date 2016年11月15日 下午9:34:56
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	private boolean initClass() {
		boolean bFlagInit = true;
		String sConfigName = "matrix-core.initclass";   
		MStringMap mStringMap = PropVisitor.getConfigMap(sConfigName);
		for (String sClassName : mStringMap.values()) {
			if (!StringUtils.isEmpty(sClassName)) {
				try {
					Class<?> cClass = ClassUtils.getClass(sClassName);
					if (cClass != null && cClass.getDeclaredMethods() != null) {
						RootInit init = (RootInit) cClass.newInstance();
						if (!init.init()) {
							bFlagInit = false;
						}
					}
				} catch (Exception e) {
					bFlagInit = false;
					this.getLogger(logger).logInfo(100090003, sClassName);
					e.printStackTrace();
				}
			}
		}
		return bFlagInit;
	}

	@Override
	public boolean onDestory() {
		boolean bFlagInit = true;
		String sConfigName = "matrix-core.initclass";
		MStringMap mStringMap = PropVisitor.getConfigMap(sConfigName);
		for (String sClassName : mStringMap.values()) {
			if (!StringUtils.isEmpty(sClassName)) {
				try {
					Class<?> cClass = ClassUtils.getClass(sClassName);
					if (cClass != null && cClass.getDeclaredMethods() != null) {
						RootInit init = (RootInit) cClass.newInstance();
						if (!init.destory()) {
							bFlagInit = false;
						}
					}
				} catch (Exception e) {
					bFlagInit = false;
					this.getLogger(logger).logInfo(100090003, sClassName);
					e.printStackTrace();
				}
			}
		}

		return bFlagInit;
	}
}
































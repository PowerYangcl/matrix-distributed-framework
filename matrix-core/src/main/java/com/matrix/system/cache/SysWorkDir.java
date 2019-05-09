package com.matrix.system.cache;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.matrix.base.BaseClass;
import com.matrix.util.IoUtil;

 
/**
 * @description: 系统配置主路径
 * @alias TopDir
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午8:35:10 
 * @version 1.0.0
 */
public class SysWorkDir extends BaseClass {

	private static Logger logger = Logger.getLogger(SysWorkDir.class);
	
	/**
	 * @description: 获取系统全局配置文件(所有配置文件)所在的临时文件夹的路径
	 * @alias upTempDir
	 * 
	 * @param sTempDir  临时目录的子文件夹
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午8:35:53 
	 * @version 1.0.0.1
	 */
	public String getTempDir(String sTempDir) {
		if (StringUtils.isEmpty(TopConst.CONST_TOP_DIR_TEMP)) {
			TopConst.CONST_TOP_DIR_TEMP = this.getServerletPath("matrixzoos/dir/temp/");   
			this.getLogger(null).sysoutInfo("系统全局配置文件所在的临时文件夹的路径初始化：" + TopConst.CONST_TOP_DIR_TEMP , this.getClass()); 
		}
		String dirPath = TopConst.CONST_TOP_DIR_TEMP + sTempDir;
		this.getLogger(null).sysoutInfo("开始创建系统全局配置文件所在的临时文件夹的路径：" + dirPath , this.getClass()); 
		IoUtil.createDir(dirPath);
		return dirPath;
	}
	

 
	/**
	 * @description: 获取程序路径
	 * 
	 * @param subDirPath
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午8:55:44 
	 * @version 1.0.0.1
	 */
	public String getServerletPath(String subDirPath) {
		String path = "";
		if (StringUtils.isBlank(TopConst.CONST_TOP_DIR_SERVLET)) {   // 在tomcat运行模式下返回的是当前应用的路径
			TopConst.CONST_TOP_DIR_SERVLET = System.getProperty("user.home"); // C:\Users\Yangcl
		}
		if (StringUtils.isNotBlank(subDirPath)) {  // 如果该参数为空 则表明不为servlet启动 可能是由juit启动
			subDirPath = "/" + subDirPath;
		}
		path = TopConst.CONST_TOP_DIR_SERVLET + subDirPath;
		this.getLogger(null).sysoutInfo("开始创建当前应用程序路径下子文件夹：" + path , this.getClass()); 
		IoUtil.createDir(path);
		return path;
	}
	
}






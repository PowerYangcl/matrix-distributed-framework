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
			this.getLogger(logger).logInfo("初始化全局的配置文件夹和系统信息：" + TopConst.CONST_TOP_DIR_TEMP); 
		}
		String dirPath = TopConst.CONST_TOP_DIR_TEMP + sTempDir;
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
		IoUtil.createDir(path);
		return path;
	}
	
	/**
	 * @description: 获取加载扩展配置目录
	 * @alias upCustomPath
	 * 
	 * @param path 目录名称 如果传入的参数以/结尾则自动创建文件夹
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午9:13:40 
	 * @version 1.0.0.1
	 */
	public String getCustomPath(String path) {
		String path_ = "";
		if (StringUtils.isBlank(TopConst.CONST_TOP_DIR_CUSTOM)) {
			String sServerPath = this.getServerletPath("");
			String sStart = "/power/matrix/";   
			// 判断如果是windows系统 则默认取系统所在路径的根目录
			if (StringUtils.substring(sServerPath, 1, 2).equals(":")) {
				sStart = sServerPath.substring(0, 2) + sStart;
			}
			sServerPath = sServerPath.toLowerCase().replace(":", "_").replace("/", "_").replace("\\", "_").replace("__", "_");   
			sStart = sStart + sServerPath;
			TopConst.CONST_TOP_DIR_CUSTOM = sStart + "/";
		}

		path_ = TopConst.CONST_TOP_DIR_CUSTOM + path;
		if (path_.endsWith("/")) {
			IoUtil.createDir(path_);
		}
		return path_;
	}
	
	

	/**
	 * @descriptions 获取本地配置目录 该目录为最后加载的配置 会覆盖所有已加载配置   
	 * @alias upLocalConfigPath
	 * 
	 * @return
	 * @date 2016年11月12日 上午12:01:42
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public String getLocalConfigPath() {
		String path = "";
		String serverPath = getServerletPath("");
		String start = "/power/matrix/local/";   
		// 判断如果是windows系统 则默认取系统所在路径的根目录
		if (StringUtils.substring(serverPath, 1, 2).equals(":")) {
			start = serverPath.substring(0, 2) + start;
		}
		path = start; 
		IoUtil.createDir(path);
		return path;
	}
}






package com.matrix.system.cache;

/**
 * @description: 顶级定义
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午8:37:26 
 * @version 1.0.0
 */
public class TopConst {

	/**
	 * 定义临时目录 该目录存放初始化的配置文件夹和系统信息 <br>
	 * 每次在servlet启动的时候会强制删除掉
	 */
	public static String CONST_TOP_DIR_TEMP = null;

	/**
	 * 
	 * 程序目录 该目录在tomcat运行模式下返回的是当前应用的路径（ServletLoader初始化）
	 * 如果该参数为空 则表明不为servlet启动 可能是由juit启动
	 *      serverlet加载时的调用
	 */
	public static String CONST_TOP_DIR_SERVLET = null;

	/**
	 * 系统默认编码 默认为utf-8
	 */
	public final static String CONST_BASE_ENCODING = "utf-8";

	/**
	 * 定义配置文件存储文件夹
	 */
//	public final static String CONST_TOP_CUSTOM_CONFIG_PATH = "config/";

}

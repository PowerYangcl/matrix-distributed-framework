package com.matrix.system.cache;

/**
 * @description: 顶级定义
 * @alias TopConst
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月11日 下午8:37:26 
 * @version 1.0.0
 */
public class TopConst {

	/**
	 * 自定义扩展目录定义
	 */
	public static String CONST_TOP_DIR_CUSTOM = null;

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
	public final static String CONST_TOP_CUSTOM_CONFIG_PATH = "config/";

	/**
	 * 高级MD5附加
	 */
	public final static String CONST_MD5_FIXED = "_zapmd5";

	/*
	 * 任务参数的起始值
	 */
	public final static String CONST_JOB_START = "job_";

	/*
	 * 定义当前整个后台系统的版本 由config.matrix-core初始化
	 */
	public static String CONST_CURRENT_VERSION = "";

	/**
	 * 定义当前的运行模型 由config.matrix-core初始化
	 */
	public static String CONST_CURRENT_MODEL = "";

	/**
	 * 定义日志类型 由config.matrix-core初始化
	 */
	public static String CONST_LOG_TYPE = "";

	/**
	 * 定义日志存储目标 由config.matrix-core初始化
	 */
	public static String CONST_LOG_ADDRESS = "";

	/*
	 * 定义缓存类型
	 */
	public static int CONST_KV_TYPE = 0;

	/**
	 * 定义JAVA基本类型
	 */
	public final static String CONST_BASE_TYPE = "string,int,float,double,boolen,long,short,bigdecimal,integer";

	/**
	 * 特殊替换字符串
	 */
	public final static String CONST_FILTER_STRING = "'*()";

}

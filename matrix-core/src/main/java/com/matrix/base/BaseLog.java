package com.matrix.base;

import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

import com.matrix.system.cache.PropVisitor;


/**
 * @deprecated
 * @description: 基础日志服务类  
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年9月29日 下午2:33:16 
 * @version 1.0.0
 */
public class BaseLog{   
	private BaseLog() {};
	private static class LazyHolder {
		private static final BaseLog INSTANCE = new BaseLog();
	}
	public static final BaseLog getInstance() {
		return LazyHolder.INSTANCE; 
	}
	
	private Logger logger = null;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public BaseLog setLogger(Logger log) {
		this.logger = log;
		return BaseLog.getInstance();
	}
	
	/**
	 * @description: 格式化日志输出
	 * 
	 * @param infoCode
	 * @param sParms
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月11日 下午6:35:30 
	 * @version 1.0.0.1
	 */
	public String formatLog(int infoCode, Object... sParms) {
		return "info-code= " + String.valueOf(infoCode) + "|" + PropVisitor.getLogInfo(infoCode, sParms);
	}
	
	
	/**
	 * @description: 统一日志输出格式
	 * 	如：
	 * 	[com.matrix.system.listener.MatrixDistributedFrameworkListener][2018-09-18 15:02:27][MatrixDistributedFrameworkListener出现二次调用 ! ! ! ! !]
	 *
	 * @param infoCode 
	 * @author Yangcl
	 * @date 2018年9月18日 下午3:07:14 
	 * @version 1.0.0.1
	 */
	public void logInfo(int infoCode , Class<?> clazz) {
		this.logger.info("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + PropVisitor.getLogInfo(infoCode) + "]");
	}
	
	public void logInfo(int infoCode , Class<?> clazz, Object... sParms) {
		this.logger.info("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + formatLog(infoCode, sParms) + "]");
	}
	
	public void logInfo(String content , Class<?> clazz) {
		this.logger.info("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + content + "]");
	}
	
	
	public void sysoutInfo(int infoCode , Class<?> clazz) {
		System.out.println("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + PropVisitor.getLogInfo(infoCode) + "]");
	}
	
	public void sysoutInfo(int infoCode , Class<?> clazz, Object... sParms) {
		System.out.println("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + formatLog(infoCode, sParms) + "]");
	}
	
	public void sysoutInfo(String content , Class<?> clazz) {
		System.out.println("[" + sdf.format(System.currentTimeMillis()) +  "][" + clazz.getName() + "][" + content + "]");
	}
	
	
	public void logError(int infoCode) {
		this.logger.error("[" + sdf.format(System.currentTimeMillis()) +  "][" + PropVisitor.getLogInfo(infoCode) + "]");
	}
	
	public void logError(int infoCode, Object... sParms) {
		this.logger.error("[" + sdf.format(System.currentTimeMillis()) +  "][" + formatLog(infoCode, sParms) + "]");
	}
	
	public void logError(String content) {
		this.logger.error("[" + sdf.format(System.currentTimeMillis()) +  "][" + content + "]");
	}
	

	
	
	
	
	
	public void logWarn(int infoCode) {
		this.logger.warn(PropVisitor.getLogInfo(infoCode));
	}
	
	public void logWarn(int infoCode, Object... sParms) {
		this.logger.warn(formatLog(infoCode, sParms));
	}
	
	public void logWarn(String content) {
		this.logger.warn(content);
	}
	
	public void logDebug(int infoCode) {
		this.logger.debug(PropVisitor.getLogInfo(infoCode));
	}
	
	public void logDebug(int infoCode, Object... sParms) {
		this.logger.debug(formatLog(infoCode, sParms));
	}
	
	public void logDebug(String content) {
		this.logger.debug(content);
	}
}




















package com.matrix.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
/**
 * @description: 异常工具封装
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年1月25日 下午3:48:19 
 * @version 1.0.0.1
 */
public class ExceptionUtils {
	
	  /**
	   * @description: 保存异常信息为文本
	   * 	此方法将返回完整的异常堆栈信息。
	   *
	   * @param e
	   * @author Yangcl
	   * @date 2018年1月25日 下午3:47:50 
	   * @version 1.0.0.1
	   */
	  public static String getExceptionInfo(Exception e) {
	    	StringWriter sw = null;
	        PrintWriter pw = null;
	        try {
	            sw = new StringWriter();
	            pw = new PrintWriter(sw);
	            e.printStackTrace(pw);
	            return "\r\n" + sw.toString() + "\r\n";
	        } catch (Exception e2) {
	            return "Error Info From Exception";
	        }finally {
	        	try {
					sw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	            pw.close();
			}
	    }
	  
}

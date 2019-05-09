package com.matrix.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @description: 解析请求者的IP地址
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年4月2日 上午9:55:24 
 * @version 1.0.0.1
 */
public class IpUtil {
	
	/**
	 * @description: 解析远端请求者的真实IP地址。
	 *
	 * @param request
	 * @author Yangcl
	 * @date 2018年4月2日 上午10:21:59 
	 * @version 1.0.0.1
	 */
	public static final JSONObject analysisRemoteHostIp(HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && ip.indexOf(",") > 0) {
			ip = ip.substring(0, ip.indexOf(","));  // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Real-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		
		// 针对非本地IP请求
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			result.put("status", "error");
    		result.put("msg", "Ip analysis failure.");
    		return result;
		}
		
		result.put("status", "success");
		result.put("firstIp", ip);
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            JSONArray info = new JSONArray();
            flag: while (netInterfaces.hasMoreElements()) {
            	JSONObject local = new JSONObject();  // 请求者本地调试
                NetworkInterface ni = netInterfaces.nextElement();
                local.put("驱动名称", ni.getDisplayName());
                local.put("网络名称", ni.getName()); 
                
                JSONArray iparr = new JSONArray();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while (ips.hasMoreElements()) {
                    ip = ips.nextElement().getHostAddress();
                    if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
                        break flag;
                    }
                    iparr.add(ip);  // IP地址
                }
                local.put("ips", iparr);
                info.add(local);
            }
            result.put("info" , info);
        }catch (Exception e) {
        	e.printStackTrace(); 
        	result.put("status", "error");
    		result.put("msg", "When analysis remote host ip:" + ip + " , we got a exception. Please see log.");
    		return result;
        }
        
		return result;
	}
	
	/**
	 * @description: 获取当前服务器IP地址
	 *
	 * @author Yangcl
	 * @date 2018年11月21日 下午3:48:47 
	 * @version 1.0.0.1
	 */
	public static String getHostIpAddress(){
		try {
			InetAddress address = InetAddress.getLocalHost();
			return address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return "";
	}
	
}






























































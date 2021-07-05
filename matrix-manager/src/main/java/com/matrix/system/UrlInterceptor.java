package com.matrix.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.view.McUserInfoView;

/**
 * @description: 页面权限拦截器|主要针对二级菜单栏
 * 
 * 拦截关键词：page_：二级菜单栏对应请求|system_：系统关键功能对应请求，通常这些请求不对系统用户开放，只对管理员开放，比如刷新字典缓存
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年5月25日 上午11:46:25 
 * @version 1.0.0
 */
public class UrlInterceptor extends HandlerInterceptorAdapter{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * 构建一个数组，数组中的请求将被排除不会被拦截。这些请求路径标识都是无需用户进行登录就可使用的
	 * 发送验证码|注册相关*.do|验证邮箱 等等应在这里进行过滤
	 */
    private static final String[] ExcludeUri = {"login.do" , "logout.do" , "validate_code.do"};  
 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url_ [] = request.getRequestURL().toString().split("/");  // 分割路径地址，取出最后一个
        String url = url_[url_.length-1];    // *.do后跟随参数也不会造成影响：request.getRequestURL().toString()不会取得*.do后面的参数
        for (String s : ExcludeUri) {
            if (url.equals(s)) {
            	return true;	
            }
        }
        if(StringUtils.startsWith(url, "api")){		// 公共调用接口则跳过验证。
        	String parameter = request.getParameter("json");
        	System.out.println(parameter);
        	return true;
        }
        
        // 如果这个".do"请求不在 ExcludeUri 数组中则验证Session是否有值，如果Session未超时则不拦截这个请求。
        HttpSession session = request.getSession();
        McUserInfoView info = (McUserInfoView) session.getAttribute("userInfo");  
        if (info != null){
        	if( url.equals("page_manager_home.do") || url.equals("page_manager_index.do")){
        		return true;	// 如果用户已经登录则可以访问首页        
        	}
        	if(StringUtils.startsWith(url, "page_")){ 
        		// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
        		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(info.getId().toString()), McUserRoleCache.class);
        		List<McSysFunction> list = cache.getMsfList();
        		for(McSysFunction sf : list){
        			if(sf.getNavType() != null && sf.getNavType() == 3){ 
        				String [] arr = sf.getFuncUrl().split("/");
        				if(arr[arr.length -1].equals(url)){
        					return true;
        				}
        			}
        		}
        	}else{
        		return true;     // 如果用户已经登录，且是非权限类型的跳转请求，则允许访问。
        	}
        }else{  // 如果用户没有登录则返回到登录页
        	String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login.jsp" ;
        	response.sendRedirect(loginUrl);
        	return false;
        }
        
        /**
         * 如果请求被排除则跳转到默认提示页面
         */
        String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/jsp/sys_page/roleErrorPage.jsp" ;
        response.sendRedirect(loginUrl);
        return false;
    }
 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}


















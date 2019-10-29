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
 * @description: 页面权限拦截器|拦截【二级菜单】和【按钮】
 * 	系统强制要求：
 * 			api.do：系统开放接口
 * 			page_：二级菜单栏对应请求，例如：page_user_list。page代表页面，user代表用户模块，list代表列表页面
 * 			ajax_：系统所有异步ajax请求必须以此开头
 * 	其他请求全部会被拦截，无法访问
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月9日 下午5:51:48 
 * @version 1.0.0.1
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
        
        if(url.equals("api.do")){		// 系统开放接口则跳过验证。
        	return true;
        }
        
        // 如果这个".do"请求不在 ExcludeUri 数组中则验证Session是否有值，如果Session未超时则不拦截这个请求。
        HttpSession session = request.getSession();
        McUserInfoView info = (McUserInfoView) session.getAttribute("userInfo");  
        if (info != null){
        	if( url.equals("page_permissions_index.do")){
        		return true;	// 如果用户已经登录则可以访问首页        
        	}
        	
        	if(StringUtils.startsWith(url, "page_")){ 
        		// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
        		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(info.getId().toString()), McUserRoleCache.class);
        		List<McSysFunction> list = cache.getMsfList();
        		for(McSysFunction sf : list) {
        			// navType：-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 内部跳转页面
        			if(sf.getNavType() != null && sf.getNavType() == 3){ 
        				String [] arr = sf.getFuncUrl().split("/");
        				if(arr[arr.length -1].equals(url)){
        					return true;
        				}
        			}
        		}
        	}
        	
        	if(StringUtils.startsWith(url, "ajax_")) {
        		if(StringUtils.startsWith(url, "ajax_btn_")) {		// 开始验证用户按钮权限
        			String btn = request.getParameter("eleValue");
        			if(StringUtils.isBlank(btn)) {
        				// 如果请求被排除则跳转到默认提示页面  				TODO 此处应该提示缺少按钮级权限->按钮权限标识丢失
        		        String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-error.html" ;
        		        response.sendRedirect(loginUrl);
        		        return false;
        			}
        			
        			// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
            		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(info.getId().toString()), McUserRoleCache.class);
            		List<McSysFunction> list = cache.getMsfList();
            		for(McSysFunction sf : list) {
            			// navType：-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 内部跳转页面
            			if(sf.getNavType() != null && sf.getNavType() > 3){ 	// 4 页面按钮|5 内部跳转页面
            				if(btn.equals(sf.getEleValue())){
            					return true;
            				}
            			}
            		}
            		
            		// 如果请求被排除则跳转到默认提示页面  				TODO 此处应该提示缺少按钮级权限->按钮权限标识错误
    		        String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-error.html" ;
    		        response.sendRedirect(loginUrl);
            		return false;
        		}
        		// 正常的ajax请求，非按钮标识
        		return true;
        	}
        	
        }else{  
        	// 如果用户没有登录则返回到登录页
        	String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login.html" ;
        	response.sendRedirect(loginUrl);
        	return false;
        }
        
        if(StringUtils.startsWith(url, "page_")) {
        	// 如果请求被排除则跳转到默认提示页面				TODO 此处应该提示缺少二级页面权限
            String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/error.html" ;
            response.sendRedirect(loginUrl);
        }else {
        	String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-error.html" ;
	        response.sendRedirect(loginUrl);
        }
        
        return false;
    }
 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}


















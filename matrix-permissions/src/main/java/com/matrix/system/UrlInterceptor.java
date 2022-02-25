package com.matrix.system;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
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
 * 			dialog_：系统页面弹框；基于layer.open打开的jsp页面，通常是弹框分页列表，极少数情况是复杂的添加/编辑页。
 * 			ajax_：系统所有异步ajax请求必须以此开头
 * 	其他请求全部会被拦截，无法访问
 * 
 * 	参考：https://www.cnblogs.com/zwqh/p/11719254.html
 * 
 * 	preHandle：在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制、权限校验等处理；
 * 	postHandle：在业务处理器处理请求执行完成后，生成视图之前执行。
 * 	afterCompletion：在 DispatcherServlet 完全处理完请求后被调用，可用于清理资源等。
 *
 * Interceptor与Filter过滤器的区别
 * 	1. 拦截器是基于java的反射机制的，而过滤器是基于函数回调。
 * 	2. 拦截器不依赖于servlet容器，而过滤器依赖于servlet容器。
 * 	3. 拦截器只能对Controller请求起作用，而过滤器则可以对几乎所有的请求起作用。
 * 	4. 拦截器可以访问action上下文、值栈里的对象，而过滤器不能访问。
 * 	5. 在Controller的生命周期中，拦截器可以多次被调用，而过滤器只能在容器初始化时被调用一次。
 * 	6. 拦截器可以获取IOC容器中的各个bean，而过滤器不行。这点很重要，在拦截器里注入一个service，可以调用业务逻辑。
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月9日 下午5:51:48 
 * @version 1.0.0.1
 */
public class UrlInterceptor extends BaseClass implements AsyncHandlerInterceptor{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * 构建一个数组，数组中的请求将被排除不会被拦截。这些请求路径标识都是无需用户进行登录就可使用的
	 * 发送验证码|注册相关*.do|验证邮箱 等等应在这里进行过滤
	 */
    private static final String[] ExcludeUri = {"login.do" , "logout.do" , "validate_code.do"};  
 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url_ [] = request.getRequestURL().toString().split("/");  // 分割路径地址，取出最后一个
        String url = url_[url_.length-1];    // *.do后跟随参数也不会造成影响：request.getRequestURL().toString()不会取得*.do后面的参数
//        if(!url.split("\\.")[1].equals("do")) {		// spring boot 比较der，放开静态资源文件
//        	return true;
//        }
        if(!StringUtils.contains(request.getRequestURL().toString(), ".do")) {
        	return true;
        }
        for (String s : ExcludeUri) {
            if (url.equals(s)) {
            	return true;	
            }
        }
        
        if(StringUtils.startsWith(url, "api")){		// 系统开放接口则跳过验证。
        	return this.openApi(request, response);
        }
        
        // 如果这个".do"请求不在 ExcludeUri 数组中则验证Session是否有值，如果Session未超时则不拦截这个请求。
        HttpSession session = request.getSession();
        McUserInfoView info = (McUserInfoView) session.getAttribute("userInfo");  
        if (info != null){
        	if( url.equals("page_permissions_index.do")){
        		return true;	// 如果用户已经登录则可以访问首页        
        	}
        	
        	if(StringUtils.startsWith(url, "dialog_")) {
        		// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
        		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString()), McUserRoleCache.class);
        		List<McSysFunction> list = cache.getMsfList();
        		for(McSysFunction sf : list) {
        			// navType：-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)
        			if(sf.getNavType() != null && sf.getNavType() == 5){ 
        				String [] arr = sf.getFuncUrl().split("/");
        				if(arr[arr.length -1].equals(url)){
        					return true;
        				}
        			}
        		}
        		
        		String ajaxErrorUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-error.html" ;
    			response.sendRedirect(ajaxErrorUrl);
    			return false;
        	}
        	
        	if(StringUtils.startsWith(url, "page_")){ 
        		// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
        		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString()), McUserRoleCache.class);
        		List<McSysFunction> list = cache.getMsfList();
        		for(McSysFunction sf : list) {
        			// navType：-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)
        			if(sf.getNavType() != null && sf.getNavType() == 3){ 
        				String [] arr = sf.getFuncUrl().split("/");
        				if(arr[arr.length -1].equals(url)){
        					return true;
        				}
        			}
        		}
        		
        		String pageErrorUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/page-error.html" ;
        		response.sendRedirect(pageErrorUrl);
		        return false;
        	}
        	
        	if(StringUtils.startsWith(url, "ajax_")) {
        		if(StringUtils.startsWith(url, "ajax_btn_")) {		// 开始验证用户按钮权限
        			String btn = request.getParameter("eleValue");
        			String ajaxErrorUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-error.html" ;
        			if(StringUtils.isBlank(btn)) {
        				// 如果请求被排除则跳转到默认提示页面
        		        response.sendRedirect(ajaxErrorUrl);
        		        return false;
        			}
        			
        			// 此时开始判断这个url 是否为该用户权限内的，如果不是，则返回false
            		McUserRoleCache cache = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString()), McUserRoleCache.class);
            		List<McSysFunction> list = cache.getMsfList();
            		for(McSysFunction sf : list) {
            			// navType：-1 根节点 0 平台标记 1 横向导航栏|2 为1级菜单栏|3 2级菜单栏 |4 页面按钮|5 按钮内包含跳转页面(dialog或新页面)
            			if(sf.getNavType() != null && sf.getNavType() == 4){
            				if(btn.equals(sf.getEleValue()) && url.equals(sf.getAjaxBtnUrl())){  // ajax_btn_*****需要与eleValue匹配
            					return true;
            				}
            			}
            		}
            		
            		// 如果请求被排除则跳转到默认提示页面
    		        response.sendRedirect(ajaxErrorUrl);
            		return false;
        		}
        		// 正常的ajax请求，非按钮标识
        		return true;
        	}
        }else{
        	if(StringUtils.startsWith(url, "ajax_")) {
        		String timeOutUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/ajax-session-timeout.html" ;
		        response.sendRedirect(timeOutUrl);
        		return false;
        	}
        	
        	// 如果用户没有登录则返回到登录页
        	String loginUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login.html" ;
        	response.sendRedirect(loginUrl);
        	return false;
        }
        
        
        // 错误的请求约定! 在系统用户登录的情况下，系统接口请求需要以page_|ajax_之一开头，其余请求将会被阻止。
        String requestError = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/views/tips/request-error.html" ;
        response.sendRedirect(requestError);
        return false;
    }
 
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
    }
    
    // TODO 
    private boolean openApi(HttpServletRequest request, HttpServletResponse response) {
    	return true;
    }
}


















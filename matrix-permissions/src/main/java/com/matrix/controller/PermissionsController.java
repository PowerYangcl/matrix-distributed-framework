package com.matrix.controller;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.matrix.base.BaseController;



/**
 * @description: 系统页面跳转控制器
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年10月9日 下午4:38:00 
 * @version 1.0.0.1
 */
@Controller
@RequestMapping("permissions")
public class PermissionsController extends BaseController{

	private static Logger logger = Logger.getLogger(PermissionsController.class);
	
	/**
	 * @description: 登录成功后跳转到主页
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2019年10月9日 下午5:35:27 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_permissions_index")
	public String pagePermissionsIndex(HttpSession session) {
		super.userBehavior(session, logger, "page_manager_home", "登录验证完成后跳转到指定页面 home.jsp");
		return "redirect:/jsp/home.jsp";
	}
	
	
	@RequestMapping("page_manager_index")
	public String loginPageIndex(HttpSession session) {
		super.userBehavior(session, logger, "page_manager_index", "登录验证完成后跳转到指定页面 index.jsp");
		return "/index";
	}
}































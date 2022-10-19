package com.matrix.controller;

import javax.servlet.http.HttpSession;

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

	/**
	 * @description: 前往系统主页
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2019年10月9日 下午5:35:27 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_permissions_index")
	public String pagePermissionsIndex(HttpSession session) {
		super.userBehavior(session, "page_manager_home", "前往系统主页界面index.jsp");
		return "views/index";
	}
	
	/**
	 * @description: 前往系统用户列表
	 * 	manager/page_manager_user_list.do
	 * 	matrix-admin/src/main/webapp/jsp/syssetting/user/sysUserList.jsp
	 *
	 * @author Yangcl
	 * @date 2019年10月18日 下午3:36:39 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_permissions_system_user_list")
	public String pagePermissionsUserList(HttpSession session){
		super.userBehavior(session, "page_permissions_system_user_list", "前往系统用户列表界面system-user-list.jsp");
		return "views/permission/user/system-user-list";
	}
	
	/**
	 * @description: 返回系统用户列表-角色列表弹窗页面
	 *
	 * @author Yangcl
	 * @date 2019年12月6日 下午5:47:59 
	 * @version 1.0.0.1
	 */
	@RequestMapping("dialog_permissions_system_role_list")
	public String dialogPermissionsSystemRoleList(HttpSession session){
		super.userBehavior(session, "dialog_permissions_system_role_list", "返回系统用户列表-角色列表弹窗页面dialog-system-role-list.jsp");
		return "views/permission/user/dialog-system-role-list";
	}
	
	/**
	 * @description: 前往系统功能列表界面
	 *
	 * @author Yangcl
	 * @date 2019年10月25日 下午10:13:20 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_permissions_system_function")
	public String pagePermissionsSystemFunction(HttpSession session){
		super.userBehavior(session, "page_permissions_system_function", "前往系统功能列表界面system-func-tree.jsp");
		return "views/permission/func/system-func-tree";
	}
	
	/**
	 * @description: 前往系统角色列表界面
	 *
	 * @author Yangcl
	 * @date 2019年10月29日 下午3:07:42 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_permissions_system_role_list")
	public String pagePermissionsSystemRoleList(HttpSession session){
		super.userBehavior(session, "page_permissions_system_role_list", "前往系统角色列表界面system-role-list.jsp");
		return "views/permission/role/system-role-list";
	}
}































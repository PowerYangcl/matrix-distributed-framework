package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
import com.matrix.util.IpUtil;

/**
 * @description: 权限、角色、后台登录等等操作 
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:11:25 
 * @version 1.0.0
 */

@Controller
@RequestMapping("manager")
public class ManagerCenterController extends BaseController{
	private static Logger logger = Logger.getLogger(ManagerCenterController.class);
	
	@Autowired
	private IMcUserInfoService mcUserInfoService;
	
	
	/**
	 * @description: 验证用户登录信息
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject login(HttpServletRequest request , McUserInfo info, HttpSession session) {
		logger.info( info.getUserName() + " - 尝试请求 - " + "login() - 方法 - " +  "正在尝试登录"); 
		
		//开始监控登录者的网卡详细信息。TODO 消息队列异步更新
		JSONObject r = IpUtil.analysisRemoteHostIp(request);
		System.out.println(r.toJSONString()); 
		
		
		return mcUserInfoService.login(info, session);
	}
	
	@RequestMapping(value = "logout", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject logout(HttpSession session) {
		super.userBehavior(session, logger, "logout", "执行退出登录");
		return mcUserInfoService.logout(session);
	}

	/**
	 * @description: 登录验证完成后跳转到指定页面
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:18:10 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_manager_home")
	public String loginPageHome(HttpSession session) {
		super.userBehavior(session, logger, "page_manager_home", "登录验证完成后跳转到指定页面 home.jsp");
		return "redirect:/jsp/home.jsp";
	}
	
	
	@RequestMapping("page_manager_index")
	public String loginPageIndex(HttpSession session) {
		super.userBehavior(session, logger, "page_manager_index", "登录验证完成后跳转到指定页面 index.jsp");
		return "/index";
	}
	
	
	/**
	 * @description: 前往用户列表页面 
	 * 
	 * @author Yangcl 
	 * @date 2017年5月18日 上午10:12:05 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_manager_user_list")
	public String systemUserList(HttpSession session){
		super.userBehavior(session, logger, "page_manager_user_list", "前往用户列表页面 sysUserList.jsp");
		return "jsp/syssetting/user/sysUserList";
	}
	
	@RequestMapping(value = "sys_user_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject sysUserList(McUserInfo info , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "sys_user_list", "获取用户列表");
		return mcUserInfoService.ajaxPageData(info, request);
	}
	
	/**
	 * @descriptions 前往添加用户界面  
	 *
	 * @return
	 * @date 2017年5月18日 下午9:59:25
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_user_add_page")
	public String showUserAddPage(HttpSession session){
		super.userBehavior(session, logger, "show_user_add_page", "页面内部跳转，前往添加用户界面sysUserAdd.jsp");
		return "jsp/syssetting/user/sysUserAdd";
	}
	
	@RequestMapping(value = "add_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addSysUser(McUserInfo info , HttpSession session) {
		super.userBehavior(session, logger, "add_sys_user", "添加用户");
		return mcUserInfoService.addSysUser(info);
	}
	
	/**
	 * @descriptions 前往修改用户信息界面  
	 *
	 * @return
	 * @date 2017年5月18日 下午10:48:39
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_user_edit_page")
	public String showUserEditPage(Long id , ModelMap model , HttpSession session){
		super.userBehavior(session, logger, "show_user_edit_page", "页面内部跳转，前往修改用户界面sysUserEdit.jsp");
		McUserInfo entity = mcUserInfoService.find(id);
		if(entity != null){
			model.addAttribute("entity", entity);
		}
		return "jsp/syssetting/user/sysUserEdit";
	}
	
	@RequestMapping(value = "edit_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editSysUser(McUserInfo info , HttpSession session) {
		super.userBehavior(session, logger, "edit_sys_user", "修改用户");
		return mcUserInfoService.editSysUser(info , session);
	}
	
	@RequestMapping(value = "ajax_manager_delete_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxManagerDeleteUser(Long id , HttpSession session) {
		super.userBehavior(session, logger, "ajax_manager_delete_user", "系统用户列表-删除用户");
		return mcUserInfoService.deleteUser(id , session);
	}
	
	
	/**
	 * @description: 用户自己选择后台页面样式风格
	 * 
	 * @param view
	 * @param session
	 * @author Yangcl 
	 * @date 2017年5月31日 下午3:02:18 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "update_page_style", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject updatePageStyle(McUserInfoView dto , HttpSession session) {
		super.userBehavior(session, logger, "update_page_style", "用户设置后台页面样式风格");
		return mcUserInfoService.updatePageStyle(dto);
	}
	
}





























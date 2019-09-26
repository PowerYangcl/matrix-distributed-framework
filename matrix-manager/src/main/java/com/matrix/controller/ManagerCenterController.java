package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
import com.matrix.support.ValidateCodeSupport;
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
	 * @description: 验证用户登录信息|PC端用户
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject login(HttpServletRequest request , McUserInfoDto info, HttpSession session) {
		logger.info( info.getUserName() + " - 尝试请求 - " + "login() - 方法 - " +  "正在尝试登录"); 
		//开始监控登录者的网卡详细信息。TODO 消息队列异步更新
//		JSONObject r = IpUtil.analysisRemoteHostIp(request);
//		System.out.println(r.toJSONString()); 
		return mcUserInfoService.login(info, session);
	}
	
	
	/**
	 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @param request
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_client_login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxClientLogin(HttpServletRequest request , McUserInfoDto dto) {
		return mcUserInfoService.ajaxClientLogin(request , dto);
	}
	
	/**
	 * @description: 获取验证码
	 *
	 * @param request
	 * @param response
	 * @param session 
	 * @author wanghao
	 * @date 2018年11月27日 上午10:20:36 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "validate_code")
	@ResponseBody
	public JSONObject validateCode() {
		ValidateCodeSupport code = new ValidateCodeSupport(100,30,4,30,25);
		return code.createValidateCode();
	}
	
	/**
	 * @description: 退出系统登录|PC端用户
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "logout", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject logout(HttpSession session) {
		super.userBehavior(session, logger, "logout", "执行退出登录");
		return mcUserInfoService.logout(session);
	}
	
	/**
	 * @description: 退出系统登录|客户端用户：nodejs/IOS平板等
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_client_logout", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxClientLogout(McUserInfoDto dto) {
		return mcUserInfoService.ajaxClientLogout(dto);
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
	
	/**
	 * @description: 用户列表页数据展示
	 *
	 * @param info
	 * @param session
	 * @param request
	 * 
	 * @author Yangcl
	 * @date 2018年9月22日 下午11:06:47 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "sys_user_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject sysUserList(McUserInfoDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "sys_user_list", "获取用户列表");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.ajaxSystemUserList(dto , request);
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
	
	/**
	 * @description: 绘制添加用户界面
	 *
	 * @param info
	 * @param session
	 * @author Yangcl
	 * @date 2018年9月22日 下午2:23:23 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_draw_add_user_page", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxDrawAddUserPage(HttpSession session) {
		super.userBehavior(session, logger, "ajax_draw_add_user_page", "绘制添加用户界面");
		return mcUserInfoService.ajaxDrawAddUserPage();
	}
	
	
	
	/**
	 * @description: 添加用户
	 *
	 * @param info
	 * @param session
	 * @author Yangcl
	 * @date 2018年9月22日 下午2:21:33 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addSysUser(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "add_sys_user", "添加用户");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
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
	
	/**
	 * @description: 获取用户详情
	 *
	 * @param dto
	 * @param session 
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_find_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindSysUser(McUserInfoDto dto , HttpSession session) {
		super.userBehavior(session, logger, "ajax_find_sys_user", "修改用户");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.ajaxFindSysUser(dto);
	}
	
	/**
	 * @description: 修改用户信息
	 *
	 * @param info
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:55:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "edit_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editSysUser(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "edit_sys_user", "修改用户信息");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.editSysUser(info);
	}
	
	/**
	 * @description: 修改用户密码
	 *
	 * @param info 
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_password_reset", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxPasswordReset(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_password_reset", "修改用户密码");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.ajaxPasswordReset(info);
	}
	
	
	/**
	 * @description: 删除一个用户|不保留数据库中的记录
	 *
	 * @param dto
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月12日 下午8:02:20 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_manager_delete_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxManagerDeleteUser(McUserInfoDto dto , HttpSession session) {
		super.userBehavior(session, logger, "ajax_manager_delete_user", "删除一个用户|不保留数据库中的记录");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.deleteUser(dto);
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
	public JSONObject updatePageStyle(McUserInfoDto dto , HttpSession session) {
		super.userBehavior(session, logger, "update_page_style", "用户设置后台页面样式风格");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.updatePageStyle(dto);
	}
	
}





























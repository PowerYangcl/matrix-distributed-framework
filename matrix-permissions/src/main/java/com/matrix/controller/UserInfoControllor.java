package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 系统后台用户控制器
 * 																											【仅matrix-manager-api项目使用】
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:30:37 
 * @version 1.0.0
 */
@Controller
@RequestMapping("userInfo")
public class UserInfoControllor  extends BaseController{
	private static Logger logger = Logger.getLogger(UserInfoControllor.class);
	
	@Autowired
	private IMcUserInfoService mcUserInfoService;
	
	
	/**
	 * @description: 验证用户登录信息|PC端用户|【仅JSP项目使用】
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "login", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject login(HttpServletRequest request , McUserInfoDto info, HttpSession session) {
		logger.info( info.getUserName() + " - 尝试请求 - " + "login() - 方法 - " +  "正在尝试登录"); 
		return mcUserInfoService.login(info, session);
	}
	
	/**
	 * @description: 系统用户列表页数据
	 * 	非Leader平台的Admin用户不应该显示在其对应的平台的用户列表中
	 * 
	 *		system-user-list.jsp根据Leader系统或者mip-web等衍生系统的不同
	 *		增加dto.type 和 dto.platform为参数条件
	 *		而cid则需要从session获取
	 *
	 * 	 dto.type：and i.type in(${type}) |Leader系统只有dto.type作为查询条件
	 * 	 dto.cid  				
	 * 	 dto.platform
	 * 	 dto.mcOrganizationId|从组织机构树拿到，查询该机构下的平台用户信息|非Leader平台会出现
	 *  	
	 * @author Yangcl
	 * @date 2019年10月18日 下午3:42:34 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_system_user_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxSystemUserList(McUserInfoDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "ajax_system_user_list", "获取系统用户列表页数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.ajaxSystemUserList(dto , request);
	}
	
	/**
	 * @description: 添加用户
	 *		
	 * @author Yangcl
	 * @date 2019年12月5日 上午10:28:56 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_add_system_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnAddSystemUser(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_add_system_user", "添加用户");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.addSysUser(info);
	}
	
	/**
	 * @description: 修改用户信息
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午2:28:38 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_edit_sys_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnEditSysUser(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_edit_sys_user", "修改用户信息");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.editSysUser(info);
	}
	
	/**
	 * @description: 修改用户密码
	 * 
	 * @author Yangcl
	 * @date 2019年12月5日 下午4:18:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_password_reset", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnPasswordReset(McUserInfoDto info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_password_reset", "修改用户密码");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.ajaxPasswordReset(info);
	}
	
	/**
	 * @description: 删除一个用户 | 不保留数据库中的记录
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午5:21:34 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_delete_system_user", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnDeleteSystemUser(McUserInfoDto dto , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_delete_system_user", "删除一个用户|不保留数据库中的记录");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcUserInfoService.deleteUser(dto);
	}
	
	/**
	 * @description: 重新加载系统用户缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_user_cache_reload", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnUserCacheReload(HttpSession session , HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_btn_user_cache_reload", "重新加载系统用户缓存");
		return mcUserInfoService.ajaxBtnUserCacheReload();
	}
	
	
	
	
	
	
}



















































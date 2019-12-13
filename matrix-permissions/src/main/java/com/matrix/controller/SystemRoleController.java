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
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcRoleService;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 系统权限控制器
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年3月1日 上午10:14:02 
 * @version 1.0.0
 */
@Controller
@RequestMapping("sysrole")
public class SystemRoleController  extends BaseController{
	private static Logger logger = Logger.getLogger(SystemRoleController.class);
	
	
	@Autowired
	private IMcSysFunctionService mcSysFunctionService;
	
	@Autowired
	private IMcRoleService mcRoleService;
	 
	
	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @param session 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_add_tree_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxAddTreeNode(McSysFunction e , HttpSession session){
		super.userBehavior(session, logger, "ajax_add_tree_node", "添加系统功能到数据库-mc_sys_function表添加记录");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.addInfo(e);	
	}
	
	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_edit_tree_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxEditTreeNode(McSysFunction e , HttpSession session){
		super.userBehavior(session, logger, "ajax_edit_tree_node", "更新一个节点到数据库");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo")); 
		return mcSysFunctionService.editInfo(e);	
	}
	
	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_update_tree_nodes", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxUpdateTreeNodes(McSysFunctionDto dto, HttpSession session){
		super.userBehavior(session, logger, "ajax_update_tree_nodes", "更新拖拽后的同层节点");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.updateTreeNodes(dto);	
	}
	
	/**
	 * @description: 删除一个系统功能节点及其子节点
	 *
	 * @param dto.ids 
	 * @author Yangcl
	 * @date 2018年10月15日 下午3:00:50 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_delete_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxDeleteNode(McSysFunctionDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_delete_node", "删除一个系统功能节点及其子节点");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.deleteNode(dto);	
	}
	
	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param dto.platform 如果不为空则获取指定平台下的功能节点|只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
	 * @param dto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_tree_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxTreeList(McSysFunctionDto dto , HttpServletRequest request , HttpSession session){
		super.userBehavior(session, logger, "ajax_tree_list", "获取树列表");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.treeList(dto);
	}

	/**
	 * @description: 系统角色列表数据
	 *
	 * @author Yangcl
	 * @date 2018年9月24日 下午3:58:48 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_system_role_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxSystemRoleList(McRoleDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "ajax_system_role_list", "系统角色列表数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.ajaxSystemRoleList(dto , request);
	}
	
	/**
	 * @description: 添加一个角色，不勾选系统功能 | ajax_btn_开头【此接口需要验证用户按钮权限】
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午9:10:56 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_add_mc_role_only", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnAddMcRoleOnly(McRole info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_add_mc_role_only", "添加一个角色，不勾选系统功能");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.addMcRole(info);
	}
	
	/**
	 * @description: 角色详情
	 * 		
	 * 		system-role-list.js没有用到
	 *
	 * @param info.id mc_role表自增id
	 * 
	 * @author Yangcl
	 * @date 2018年10月13日 下午3:37:05 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_find_role_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindRoleInfo(McRole info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_find_role_info", "角色详情");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.ajaxFindRoleInfo(info);
	}
	
	/**
	 * @descriptions 修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_edit_mc_role_only", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnEditMcRoleOnly(McRoleDto info , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_edit_mc_role_only", "修改角色名称和描述，不勾选系统功能");
		info.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.editSysRole(info);
	}
	
	/**
	 * @description: 删除系统角色
	 * 
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_delete_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnDeleteMcRole(McRoleDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_delete_mc_role", "删除系统角色");
		return mcSysFunctionService.deleteMcRole(dto);	
	}
	
	/**
	 * @description: 修改角色功能|【角色列表】->【角色功能】->【提交】按钮
	 * 
	 * @param d
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_edit_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnEditMcRole(McRoleDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_edit_mc_role", "修改角色功能|【角色列表】->【角色功能】->【提交】按钮");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.editMcRole(dto);	
	}
	
	/**
	 * @description: 修改角色功能|【角色列表】->【角色功能】->【解绑】按钮|TODO 尚未在matrix-manager-api中添加类
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_relieve_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnRelieveMcRole(McRoleDto dto , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_relieve_mc_role", "修改角色功能|【角色列表】->【角色功能】->【解绑】按钮");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.ajaxBtnRelieveMcRole(dto);	
	}
	
	
	/**
	 * @description: 重新加载系统字典缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_user_cache_reload", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnUserCacheReload(HttpSession session , HttpServletRequest request){
		super.userBehavior(session, logger, "ajax_btn_user_cache_reload", "重新加载系统字典缓存");
		return mcSysFunctionService.ajaxBtnUserCacheReload();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @descriptions 展示权限列表|如果用户已经有权限了则标识出来
	 *
	 * @date 2017年5月24日 上午12:02:01
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "user_role_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject userRoleList(McRoleDto role , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "user_role_list", "展示权限列表|如果用户已经有权限了则标识出来");  
		role.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.userRoleList(role , request);
	}
	
	
	

	
	/**
	 * @description: 创建系统角色                                       TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   未找到页面直接使用的地方
	 * 
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月11日 上午10:45:06 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addMcRole(McRoleDto dto , HttpSession session){
		super.userBehavior(session, logger, "add_mc_role", "创建系统角色");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.addMcRole(dto);	
	}

	
	/**
	 * @description: 提供功能角色支撑
	 *
	 * @param entity
	 * @param request      
	 * @author Yangcl
	 * @date 2018年2月5日 下午2:18:32 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "api_func_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFuncRole(McUserInfo entity , HttpServletRequest request , HttpSession session){
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.ajaxFuncRole(entity, request);
	}
	
	
	/**
	 * @description: 关联用户与某一个角色
	 * 
	 * @param entity
	 * @author Yangcl 
	 * @date 2017年4月20日 下午7:29:12 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_sysrole_add_user_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxSysroleAddUserRole(McUserRole entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_sysrole_add_user_role", "关联用户与某一个角色");
		entity.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcSysFunctionService.addUserRole(entity);
	}

	
	/**
	 * @description: 解除角色绑定，同时删除缓存
	 * 
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月24日 下午3:27:22 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "remove_user_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteUserRole(McUserRoleDto dto , HttpSession session){
		super.userBehavior(session, logger, "remove_user_role", "解除角色绑定，同时删除缓存");
		return mcSysFunctionService.deleteUserRole(dto);	
	}
	

}






















































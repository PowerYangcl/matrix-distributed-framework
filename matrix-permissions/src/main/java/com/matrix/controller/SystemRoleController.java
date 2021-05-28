package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.request.AddMcRoleRequest;
import com.matrix.pojo.request.AddMcUserRoleRequest;
import com.matrix.pojo.request.DeleteMcRoleRequest;
import com.matrix.pojo.request.DeleteMcUserRoleRequest;
import com.matrix.pojo.request.FindMcRoleRequest;
import com.matrix.pojo.request.FindUserRoleListRequest;
import com.matrix.pojo.request.UpdateMcRoleRequest;
import com.matrix.pojo.request.UpdateMcRoleTreeRequest;
import com.matrix.pojo.view.McRoleView;
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
	private IMcRoleService mcRoleService;
	@Autowired
	private IMcSysFunctionService mcSysFunctionService;
	
	 
	
	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
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
	public Result<PageInfo<McRoleView>> ajaxSystemRoleList(FindMcRoleRequest param , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "ajax_system_role_list", "系统角色列表数据");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.ajaxSystemRoleList(param , request);
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
	public Result<?> ajaxBtnAddMcRoleOnly(AddMcRoleRequest param , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_add_mc_role_only", "添加一个角色，不勾选系统功能");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.addMcRole(param);
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
	public Result<?> ajaxBtnEditMcRoleOnly(UpdateMcRoleRequest param , HttpSession session) {
		super.userBehavior(session, logger, "ajax_btn_edit_mc_role_only", "修改角色名称和描述，不勾选系统功能");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.editSysRole(param);
	}
	
	/**
	 * @description: 删除系统角色|系统权限配置 / 系统用户相关 / 系统角色列表
	 * 							 判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_delete_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnDeleteMcRole(DeleteMcRoleRequest param , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_delete_mc_role", "删除系统角色|系统权限配置 / 系统用户相关 / 系统角色列表");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.deleteMcRole(param);	
	}
	
	/**
	 * @description: 修改角色所关联的系统功能|【角色列表】->【角色功能】->【提交】按钮
	 * 							 在系统功能树(ztree)中勾选选中的功能点与这个角色进行关联
	 * 
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_edit_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<McRoleCache> ajaxBtnEditMcRole(UpdateMcRoleTreeRequest param , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_edit_mc_role", "修改角色所关联的系统功能|【角色列表】->【角色功能】->【提交】按钮");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.editMcRole(param);	
	}
	
	/**
	 * @description: 在系统功能树(ztree)中解绑与这个角色关联的功能点|【角色列表】->【角色功能】->【解绑】按钮|
	 * 		TODO 尚未在matrix-manager-api中添加类
	 *								 
	 * @param dto.mcRoleId
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_relieve_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnRelieveMcRole(UpdateMcRoleTreeRequest param , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_relieve_mc_role", "在系统功能树(ztree)中解绑与这个角色关联的功能点|【角色列表】->【角色功能】->【解绑】按钮");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.ajaxBtnRelieveMcRole(param);	
	}
	
	/**
	 * @description: 展示权限列表|如果用户没有这个权限了则标识为【分配】，如果已经有了这个按钮则标识位【取消】
	 * 
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发的弹框中显示的列表
	 *
	 * @param role.userId
	 * @param role.platform
	 * 
	 * @author Yangcl
	 * @date 2019年12月16日 下午4:15:53 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_user_role_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<PageInfo<McRoleView>> ajaxUserRoleList(FindUserRoleListRequest param , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "user_role_list", "系统权限配置 / 系统用户相关 / 系统用户列表-【展示权限列表】");  
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.userRoleList(param , request);
	}
	
	/**
	 * @description: 给指定用户分配一个角色
	 * 		系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【分配】按钮
	 *
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:30:40 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_allot_user_role_submit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnAllotUserRoleSubmit(AddMcUserRoleRequest param , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_allot_user_role_submit", "给指定用户分配一个角色<系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【分配】按钮>");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.allotUserRole(param);
	}
	
	/**
	 * @description: 解除角色绑定，同时删除缓存
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【取消】按钮
	 *
	 * @param dto.userId
	 * @param dto.mcRoleId
	 *  
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:39:55 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_allot_user_role_remove", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnAllotUserRoleRemove(DeleteMcUserRoleRequest param , HttpSession session){
		super.userBehavior(session, logger, "ajax_btn_allot_user_role_remove", "解除角色绑定，同时删除缓存");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.deleteUserRole(param);
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

	
	/////////////////////////////////////////////////////////////////////////////////  jsp系统中未使用到的接口  ///////////////////////////////////////////////////////////////////////////////// 
	
	/**
	 * @description: 角色详情【仅matrix-manager-api项目使用】
	 * 		
	 * @param info.id mc_role表自增id
	 * 
	 * @author Yangcl
	 * @date 2018年10月13日 下午3:37:05 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_find_role_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<McRole> ajaxFindRoleInfo(FindMcRoleRequest param , HttpSession session) {
		super.userBehavior(session, logger, "ajax_find_role_info", "角色详情");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return mcRoleService.ajaxFindRoleInfo(param);
	}
	
	
	
	

}









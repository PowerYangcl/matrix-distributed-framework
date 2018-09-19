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
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.service.IManagerCenterService;
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
	private IManagerCenterService service;  // 系统权限主服务
	
	@Autowired
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Autowired
	private IMcRoleService mcRoleService;
	 
	
	/**
	 * @description: 前往树形维护页面
	 * 
	 * @param model 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
/*	@RequestMapping("tree_page_index") 
	public String treePageIndex(ModelMap model , HttpSession session){
//		McSysFunction e = new McSysFunction();	
//		e.setFlag(1);
//		model.put("jsonTree", mcSysFunctionService.jsonList(e)); 
		super.userBehavior(session, logger, "tree_page_index", "前往树形维护页面sysFunction.jsp");
		return "jsp/syssetting/sysFunction"; 
	}*/
	
	@RequestMapping(value = "tree_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject treeList(HttpServletRequest request , HttpSession session){
		super.userBehavior(session, logger, "tree_list", "获取树列表");
		return mcSysFunctionService.treeList(request);
	}
	
	

	/**
	 * @description: 前往权限列表页 
	 * 
	 * @param model
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月19日 下午2:05:03 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_sysrole_mc_role_list") 
	public String sysMcRolePage(ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "page_sysrole_mc_role_list", "前往权限列表页面sysMcRoleList.jsp");
		return "jsp/syssetting/role/sysMcRoleList";   
	}
	
	@RequestMapping(value = "sys_role_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject sysRoleList(McRole role , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "sys_role_list", "获取权限列表页面数据");
		return mcRoleService.ajaxPageData(role, request);
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
		return mcRoleService.userRoleList(role, request);
	}
	
	
	
	/**
	 * @description: 前往添加权限页面 
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午8:41:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_role_add_page") 
	public String sysMcRoleAddPage(ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "show_role_add_page", "前往添加权限页面sysMcRoleAdd.jsp");
		return "jsp/syssetting/role/sysMcRoleAdd";   
	}
	
	/**
	 * @description: 添加一个角色，不勾选系统功能|如果同时需要勾选系统功能请使用 @ public JSONObject addMcRole(McRoleCache d , HttpSession session)
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午9:10:56 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_mc_role_only", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addMcRoleOnly(McRole info , HttpSession session) {
		super.userBehavior(session, logger, "add_mc_role_only", "添加一个角色，不勾选系统功能|如果同时需要勾选系统功能请使用 @ public JSONObject addMcRole(McRoleCache d , HttpSession session)");
		return mcRoleService.addMcRole(info , session);
	}
	
	/**
	 * @description: 前往修改权限页面 
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午10:00:02 
	 * @version 1.0.0.1
	 */
	@RequestMapping("show_role_edit_page") 
	public String sysMcRoleEditPage(Long id , ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "show_role_edit_page", "前往修改权限页面sysMcRoleEdit.jsp");
		McRole entity = mcRoleService.find(id);
		if(entity != null){
			model.addAttribute("entity", entity);  
		}
		return "jsp/syssetting/role/sysMcRoleEdit";   
	}
	
	/**
	 * @descriptions 修改角色名称和描述，不勾选系统功能|如果同时需要勾选系统功能请使用 @ public JSONObject editMcRole(McRoleCache d , HttpSession session)
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "edit_mc_role_only", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editMcRoleOnly(McRole info , HttpSession session) {
		super.userBehavior(session, logger, "edit_mc_role_only", "修改角色名称和描述，不勾选系统功能|如果同时需要勾选系统功能请使用 @ public JSONObject editMcRole(McRoleCache d , HttpSession session)");
		return mcRoleService.editSysRole(info , session);
	}
	
	
	/**
	 * @description: 前往系统功能页面  
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月23日 下午2:27:57 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_sysrole_function") 
	public String mcSysFunctionPage(HttpSession session){  
		super.userBehavior(session, logger, "page_sysrole_function", "前往系统功能页面mcSysFunction.jsp");
		return "jsp/syssetting/func/mcSysFunction";   
	}
	
	
	
	/**
	 * @description: 获取有效商户列表 
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午10:57:09 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "company_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject companyList(){
		return service.companyList();
	}
	
	/**
	 * @description: 添加一个节点到数据库
	 * 
	 * @param e
	 * @param session 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_tree_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addTreeNode(McSysFunction e , HttpSession session){
		super.userBehavior(session, logger, "add_tree_node", "添加一个节点到数据库");
		return mcSysFunctionService.addInfo(e, session);	
	}
	
	/**
	 * @description: 更新一个节点到数据库
	 * 
	 * @param e
	 * @param session
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "edit_tree_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editTreeNode(McSysFunction e , HttpSession session){
		super.userBehavior(session, logger, "edit_tree_node", "更新一个节点到数据库");
		return mcSysFunctionService.editInfo(e, session);	
	}
	
	/**
	 * @description: 更新拖拽后的同层节点
	 * 
	 * @param ustring id@seqnum,id@seqnum 
	 * @param session
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "update_tree_nodes", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject updateTreeNodes(String ustring , HttpSession session){
		super.userBehavior(session, logger, "update_tree_nodes", "更新拖拽后的同层节点");
		return mcSysFunctionService.updateTreeNodes(ustring, session);	
	}
	
	
	@RequestMapping(value = "delete_node", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteNode(String ids , HttpSession session){
		super.userBehavior(session, logger, "delete_node", "删除一个节点");
		return mcSysFunctionService.deleteNode(ids, session);	
	}
	
	/**
	 * @description: 创建系统角色
	 * 
	 * @param d
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月11日 上午10:45:06 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addMcRole(McRoleCache d , HttpSession session){
		super.userBehavior(session, logger, "add_mc_role", "创建系统角色");
		return mcSysFunctionService.addMcRole(d, session);	
	}
	
	/**
	 * @deprecated 方法废弃 
	 * @description: 修改系统角色
	 * 
	 * @param d
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "edit_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editMcRole(McRoleCache d , HttpSession session){
		super.userBehavior(session, logger, "edit_mc_role", "修改系统角色");
		return mcSysFunctionService.editMcRole(d, session);	
	}
	
	/**
	 * @description: 删除系统角色
	 * 
	 * @param d
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "delete_mc_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteMcRole(McRoleCache d , HttpSession session){
		super.userBehavior(session, logger, "delete_mc_role", "删除系统角色");
		return mcSysFunctionService.deleteMcRole(d, session);	
	}
	
	
	/**
	 * @description: master online message
	 * 	Send a message to master when someone sign in and user name start with admin
	 *
	 * @param entity
	 * @param request
	 * @author Yangcl
	 * @date 2018年2月5日 下午2:18:32 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "api_master_online", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject mcUserList(McUserInfo entity , HttpServletRequest request){
		return mcSysFunctionService.ajaxMasterOnline(entity, request);
	}
	
	
	/**
	 * @description: 关联用户与某一个角色
	 * 
	 * @param entity
	 * @return
	 * @author Yangcl 
	 * @date 2017年4月20日 下午7:29:12 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_sysrole_add_user_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxSysroleAddUserRole(McUserRole entity , HttpSession session){
		super.userBehavior(session, logger, "ajax_sysrole_add_user_role", "关联用户与某一个角色");
		return mcSysFunctionService.addUserRole(entity , session);
	}
	
	/**
	 * @description: 解除角色绑定，同时删除缓存
	 * 
	 * @param d
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月24日 下午3:27:22 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "remove_user_role", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteUserRole(McUserRoleDto d , HttpSession session){
		super.userBehavior(session, logger, "remove_user_role", "解除角色绑定，同时删除缓存");
		return mcSysFunctionService.deleteUserRole(d, session);	
	}
	
	
	/**
	 * @description: 重新加载系统字典缓存
	 * 
	 * @param session
	 * @author Yangcl 
	 * @date 2017年4月24日 下午2:43:35 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "system_sysrole_dict_cache_reload", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject sysDictCacheReload(HttpSession session , HttpServletRequest request){
		super.userBehavior(session, logger, "sys_dict_cache_reload", "重新加载系统字典缓存");
		return mcSysFunctionService.sysDictCacheReload();
	}
	
}






















































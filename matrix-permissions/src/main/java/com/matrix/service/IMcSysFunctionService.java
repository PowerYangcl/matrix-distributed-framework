package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McSysFunctionView;

public interface IMcSysFunctionService  extends IBaseService<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> {

	public JSONObject addInfo(McSysFunction entity);
	
	public JSONObject editInfo(McSysFunction entity);

	public JSONObject updateTreeNodes(McSysFunctionDto dto);

	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param dto.platform 如果不为空则获取指定平台下的功能节点
	 * @param dto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	public JSONObject treeList(McSysFunctionDto dto);

	public JSONObject deleteNode(McSysFunctionDto dto);

	public JSONObject addMcRole(McRoleDto dto);

	public JSONObject editMcRole(McRoleDto dto);

	public JSONObject deleteMcRole(McRoleDto dto);

	/**
	 * @description: 给指定用户分配一个角色
	 * 		系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【分配】按钮
	 *
	 * @param entity.mcRoleId
	 * @param entity.mcUserId
	 *  
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:30:40 
	 * @version 1.0.0.1
	 */
	public JSONObject addUserRole(McUserRole entity);

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
	public JSONObject deleteUserRole(McUserRoleDto d);

	/**
	 * @description: 重新加载系统字典缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnUserCacheReload();

	public JSONObject ajaxFuncRole(McUserInfo entity, HttpServletRequest request);

	/**
	 * @description: 修改角色功能|【角色列表】->【角色功能】->【解绑】按钮
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnRelieveMcRole(McRoleDto dto);            
	
}




















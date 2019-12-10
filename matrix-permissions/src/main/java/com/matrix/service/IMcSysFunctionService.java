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

	public JSONObject addUserRole(McUserRole entity);

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
	public JSONObject ajaxRelieveMcRole(McRoleDto dto);            
	
}




















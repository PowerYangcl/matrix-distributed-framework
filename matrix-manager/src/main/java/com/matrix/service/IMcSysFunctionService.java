package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McSysFunctionView;

public interface IMcSysFunctionService  extends IBaseService<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> {

	public JSONObject addInfo(McSysFunction entity , HttpSession session);
	
	public JSONObject editInfo(McSysFunction entity , HttpSession session);

	public JSONObject updateTreeNodes(String ustring, HttpSession session);

	public JSONObject treeList(HttpServletRequest request);

	public JSONObject deleteNode(String ids , HttpSession session);

	public JSONObject addMcRole(McRoleCache d, HttpSession session);

	public JSONObject editMcRole(McRoleCache d, HttpSession session);

	public JSONObject deleteMcRole(McRoleCache d, HttpSession session);

	public JSONObject addUserRole(McUserRole entity , HttpSession session);

	public JSONObject deleteUserRole(McUserRoleDto d, HttpSession session);

	public JSONObject sysDictCacheReload();

	public JSONObject ajaxMasterOnline(McUserInfo entity, HttpServletRequest request);            
	
}

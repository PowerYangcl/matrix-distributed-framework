package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McSysFunctionView;

/**
 * @description: 系统权限功能树服务支撑
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月18日 下午3:33:02 
 * @version 1.0.0.1
 */
public interface IMcSysFunctionService  extends IBaseService<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> {

	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @param session 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	public JSONObject addInfo(McSysFunction entity);
	
	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	public JSONObject editInfo(McSysFunction entity);
	
	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param dto.ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	public JSONObject updateTreeNodes(McSysFunctionDto dto);

	/**
	 * @description: 删除一个系统功能节点及其子节点
	 *
	 * @param dto.ids 
	 * @author Yangcl
	 * @date 2018年10月15日 下午3:00:50 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteNode(McSysFunctionDto dto);
	
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
	public JSONObject treeList(McSysFunctionDto dto);
	
	public JSONObject ajaxFuncRole(McUserInfo entity, HttpServletRequest request);
	
	
}




















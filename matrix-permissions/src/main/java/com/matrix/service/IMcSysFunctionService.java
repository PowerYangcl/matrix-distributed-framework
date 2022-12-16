package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.request.AddMcSysFunctionRequest;
import com.matrix.pojo.request.DeleteMcSysFunctionRequest;
import com.matrix.pojo.request.FindTreeListRequest;
import com.matrix.pojo.request.UpdateMcSysFunctionRequest;
import com.matrix.pojo.view.McSysFunctionView;
import com.matrix.pojo.view.TreeListView;

/**
 * @description: 系统权限功能树服务支撑
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月18日 下午3:33:02 
 * @version 1.0.0.1
 */
public interface IMcSysFunctionService extends IBaseService<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> {

	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	public Result<McSysFunction> addMcSysFunction(@Valid AddMcSysFunctionRequest param);
	
	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	public Result<McSysFunction> editMcSysFunction(@Valid UpdateMcSysFunctionRequest param);
	
	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	public Result<?> updateTreeNodes(@Valid UpdateMcSysFunctionRequest param);

	/**
	 * @description: 物理删除一个系统功能节点及其子节点
	 *
	 * @param param.ids 
	 * @author Yangcl
	 * @date 2018年10月15日 下午3:00:50 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteNode(@Valid DeleteMcSysFunctionRequest param);
	
	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param param.platform 如果不为空则获取指定平台下的功能节点|只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
	 * @param param.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	public Result<TreeListView> treeList(@Valid FindTreeListRequest param);
	
	public Result<?> ajaxFuncRole(McUserInfo entity, HttpServletRequest request);
	
	/**
	 * @description: 获取平台信息列表|ManagerCenterController使用
	 *
	 * @author Yangcl
	 * @date 2019年12月20日 下午3:31:38 
	 * @version 1.0.0.1
	 */
	public Result<List<McSysFunction>> ajaxPlatformInfoList(HttpSession session);
}




















package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import com.github.pagehelper.PageInfo;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.request.AddMcRoleRequest;
import com.matrix.pojo.request.AddMcUserRoleRequest;
import com.matrix.pojo.request.DeleteMcRoleRequest;
import com.matrix.pojo.request.DeleteMcUserRoleRequest;
import com.matrix.pojo.request.FindMcRoleRequest;
import com.matrix.pojo.request.FindUserRoleListRequest;
import com.matrix.pojo.request.UpdateMcRoleRequest;
import com.matrix.pojo.request.UpdateMcRoleTreeRequest;
import com.matrix.pojo.view.McRoleView;

/**
 * @description: 系统角色支撑
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月18日 下午3:48:30 
 * @version 1.0.0.1
 */
public interface IMcRoleService extends IBaseService<Long , McRole , McRoleDto , McRoleView> {
	
	/**
	 * @description: 系统角色列表数据
	 *
	 * @author Yangcl
	 * @date 2018年9月24日 下午3:58:48 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<McRoleView>> ajaxSystemRoleList(FindMcRoleRequest param, HttpServletRequest request);
	
	/**
	 * @description: 添加一个角色，不勾选系统功能 | ajax_btn_开头【此接口需要验证用户按钮权限】
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午9:10:56 
	 * @version 1.0.0.1
	 */
	public Result<?> addMcRole(AddMcRoleRequest param);
	
	/**
	 * @descriptions 修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Result<?> editSysRole(UpdateMcRoleRequest param);
	
	/**
	 * @description: 删除系统角色|系统权限配置 / 系统用户相关 / 系统角色列表
	 * 							 判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除
	 *
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteMcRole(DeleteMcRoleRequest param);
	
	/**
	 * @description: 修改角色所关联的系统功能|【角色列表】->【角色功能】->【提交】按钮
	 * 							 在系统功能树(ztree)中勾选选中的功能点与这个角色进行关联
	 * 
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	public Result<McRoleCache> editMcRole(UpdateMcRoleTreeRequest param);
	
	/**
	 * @description: 在系统功能树(ztree)中解绑与这个角色关联的功能点|【角色列表】->【角色功能】->【解绑】按钮|
	 * 		TODO 尚未在matrix-manager-api中添加类
	 *								 
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnRelieveMcRole(UpdateMcRoleTreeRequest param);
	
	/**
	 * @description: 展示权限列表|如果用户没有这个权限了则标识为【分配】，如果已经有了这个按钮则标识位【取消】
	 * 
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发的弹框中显示的列表
	 *
	 * @author Yangcl
	 * @date 2019年12月16日 下午4:15:53 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<McRoleView>> userRoleList(FindUserRoleListRequest param , HttpServletRequest request);

	/**
	 * @description: 给指定用户分配一个角色
	 * 		系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【分配】按钮
	 *
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:30:40 
	 * @version 1.0.0.1
	 */
	public Result<?> allotUserRole(AddMcUserRoleRequest param);
	
	/**
	 * @description: 解除角色绑定，同时删除缓存
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【取消】按钮
	 *
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:39:55 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteUserRole(DeleteMcUserRoleRequest param);
	
	/**
	 * @description: 权限详情【仅matrix-manager-api项目使用】
	 *
	 * @author Yangcl
	 * @date 2018年10月13日 下午3:37:05 
	 * @version 1.0.0.1
	 */
	public Result<McRole> ajaxFindRoleInfo(FindMcRoleRequest param);

}

























package com.matrix.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseView;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.request.AddMcUserInfoRequest;
import com.matrix.pojo.request.DeleteMcUserInfoRequest;
import com.matrix.pojo.request.FindLoginRequest;
import com.matrix.pojo.request.FindLogoutRequest;
import com.matrix.pojo.request.FindMcUserInfoListRequest;
import com.matrix.pojo.request.FindMcUserInfoRequest;
import com.matrix.pojo.request.UpdateMcUserInfoPasswordRequest;
import com.matrix.pojo.request.UpdateMcUserInfoRequest;
import com.matrix.pojo.view.ClientLoginView;
import com.matrix.pojo.view.LoginView;
import com.matrix.pojo.view.McUserInfoView;

public interface IMcUserInfoService extends IBaseService<Long , McUserInfo , McUserInfoDto , BaseView> { 
	
	/**
	 * @description: 验证用户登录信息|PC端用户|【仅JSP项目使用】
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	public Result<LoginView> login(FindLoginRequest param , HttpSession session);

	/**
	 * @description: 退出系统|PC端用户|【仅JSP项目使用】
	 *
	 * @author Yangcl
	 * @date 2019年12月19日 下午2:41:27 
	 * @version 1.0.0.1
	 */
	public Result<?> logout(HttpSession session);
	
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
	public Result<PageInfo<McUserInfoView>> ajaxSystemUserList(@Valid FindMcUserInfoListRequest param , HttpServletRequest request);
	
	/**
	 * @description: 添加用户
	 *		
	 * @author Yangcl
	 * @date 2019年12月5日 上午10:28:56 
	 * @version 1.0.0.1
	 */
	public Result<?> addSysUser(@Valid AddMcUserInfoRequest param);
	
	/**
	 * @description: 修改用户信息
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午2:28:38 
	 * @version 1.0.0.1
	 */
	public Result<?> editSysUser(@Valid UpdateMcUserInfoRequest param);
	
	/**
	 * @description: 修改用户密码
	 *
	 * @param info 
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxPasswordReset(@Valid UpdateMcUserInfoPasswordRequest param);
	
	/**
	 * @description: 删除一个用户 | 不保留数据库中的记录
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午5:21:34 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteUser(@Valid DeleteMcUserInfoRequest param);
	
	/**
	 * @description: 重新加载系统用户缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnUserCacheReload();
	
	/**
	 * @description: 获取平台信息列表|ManagerCenterController使用
	 *
	 * @return 
	 * @author Yangcl
	 * @date 2019年12月20日 下午3:31:38 
	 * @version 1.0.0.1
	 */
	public Result<List<McSysFunction>> ajaxPlatformInfoList();
	

	
	
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @param request
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	public Result<ClientLoginView> ajaxClientLogin(@Valid FindLoginRequest param, HttpServletRequest request);

	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		退出系统登录|客户端用户：nodejs/IOS平板等
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxClientLogout(@Valid FindLogoutRequest param);
	



	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 	获取用户详情
	 *
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	public Result<McUserInfoView> ajaxFindSysUser(@Valid FindMcUserInfoRequest param);

}






























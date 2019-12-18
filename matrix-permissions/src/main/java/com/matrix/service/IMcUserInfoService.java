package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseView;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;

public interface IMcUserInfoService extends IBaseService<Long , McUserInfo , McUserInfoDto , BaseView> { 
	
	/**
	 * @description: 验证用户登录信息|PC端用户|【仅JSP项目使用】
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	public JSONObject login(McUserInfoDto userInfo , HttpSession session);

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
	public JSONObject ajaxSystemUserList(McUserInfoDto dto ,HttpServletRequest request);
	
	/**
	 * @description: 添加用户
	 *		
	 * @author Yangcl
	 * @date 2019年12月5日 上午10:28:56 
	 * @version 1.0.0.1
	 */
	public JSONObject addSysUser(McUserInfoDto info);
	
	/**
	 * @description: 修改用户信息
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午2:28:38 
	 * @version 1.0.0.1
	 */
	public JSONObject editSysUser(McUserInfoDto info);
	
	/**
	 * @description: 修改用户密码
	 *
	 * @param info 
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPasswordReset(McUserInfoDto info);
	
	/**
	 * @description: 删除一个用户 | 不保留数据库中的记录
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午5:21:34 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteUser(McUserInfoDto dto);
	
	/**
	 * @description: 重新加载系统用户缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnUserCacheReload();
	
	
	
	
	
	
	
	
	
	


	

	
	

	public JSONObject logout(HttpSession session);

	public JSONObject updatePageStyle(McUserInfoDto dto);

	public JSONObject ajaxPlatformInfoList();


	/**
	 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @param request
	 * @param dto
	 * 
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxClientLogin(HttpServletRequest request, McUserInfoDto dto);

	/**
	 * @description: 退出系统登录|客户端用户：nodejs/IOS平板等
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxClientLogout(McUserInfoDto dto);

	/**
	 * @description: 获取用户详情
	 *
	 * @param dto
	 * @param session 
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindSysUser(McUserInfoDto dto);

}






























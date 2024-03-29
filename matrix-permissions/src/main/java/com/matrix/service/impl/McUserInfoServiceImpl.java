package com.matrix.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.matrix.pojo.request.AddMcUserInfoRequest;
import com.matrix.pojo.request.DeleteMcUserInfoRequest;
import com.matrix.pojo.request.FindLoginRequest;
import com.matrix.pojo.request.FindLogoutRequest;
import com.matrix.pojo.request.FindMcUserInfoListRequest;
import com.matrix.pojo.request.FindMcUserInfoRequest;
import com.matrix.pojo.request.UpdateMcUserInfoPasswordRequest;
import com.matrix.pojo.request.UpdateMcUserInfoRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.BaseView;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.ClientLoginView;
import com.matrix.pojo.view.LoginView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
import com.matrix.util.SignUtil;


/**
 * @description: 系统后台用户控制器
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2016年11月25日 下午3:30:37 
 * @version 1.0.0.1
 */
@Validated
@Service("mcUserInfo") 
public class McUserInfoServiceImpl extends BaseServiceImpl<Long , McUserInfo , McUserInfoDto , BaseView> implements IMcUserInfoService{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IMcUserInfoMapper mcUserInfoMapper;
	
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper;
	
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;

	/**
	 * @description: 验证用户登录信息|PC端用户|【仅JSP项目使用】
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	public Result<LoginView> login(FindLoginRequest param , HttpSession session) {
		String userInfoNpJson = launch.loadDictCache(CachePrefix.UserInfoNp , "UserInfoNpInit").get(param.getUserName() + "," + SignUtil.md5Sign(param.getPassword()));
		if (StringUtils.isBlank(userInfoNpJson)) {		// 101010017=用户名或密码错误
			return Result.ERROR(this.getInfo(101010017), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		if(StringUtils.isBlank(info.getPlatform()) || !StringUtils.contains(info.getPlatform(), param.getPlatform())) {	// 101010023=未授权用户，平台未对您分配权限，标识码：{0}
			return Result.ERROR(this.getInfo(101010023 , param.getPlatform()), ResultCode.INTERNAL_VALIDATION_FAILED);	// 此处会自动拦截admin类型用户登录leader系统
		}
		info.setWebcode(param.getPlatform());
		String pageJson = launch.loadDictCache(CachePrefix.McUserRole , "McUserRoleInit").get(param.getPlatform() + "@" + info.getId().toString());  
		if(StringUtils.isBlank(pageJson)) {  	// 101010022=未授权用户
			return Result.ERROR(this.getInfo(101010022), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		// 如果你的两个窗口是同属一个网站，且在同一个用户名下，那么session也是一样的；如果不是，那么session也不一样。
		session.setAttribute("userInfo", info);   // 写入session
		
		LoginView view = new LoginView();
		view.setInfo(userInfoNpJson);
		view.setPageJson(pageJson);
		view.setUploadUrl(this.getConfig("matrix-core.ajax_file_upload_" + this.getConfig("matrix-core.model")));	// 系统文件上传
		return Result.SUCCESS(view);
	}
	
	/**
	 * @description: 退出系统|PC端用户|【仅JSP项目使用】
	 *
	 * @author Yangcl
	 * @date 2019年12月19日 下午2:41:27 
	 * @version 1.0.0.1
	 */
	public Result<?> logout(HttpSession session) {		// 101010015=系统已经退出
		session.invalidate();
		return Result.SUCCESS(this.getInfo(101010015));
	}
	
	/**
	 * @description: 系统用户列表页数据
	 * 		leader用户在【矩阵分布式框架控制台】中查看leader和所有平台的admin用户信息，但admin用户无法登录【矩阵分布式框架控制台】系统。
	 * 		admin用户可以登录其已经分配platform标识码的系统，但那个系统仅展示user类型用户。
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
	public Result<PageInfo<McUserInfoView>> ajaxSystemUserList(@Valid FindMcUserInfoListRequest param , HttpServletRequest request) {
		Result<PageInfo<McUserInfoView>> validate = param.validateAjaxSystemUserList();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		McUserInfoDto dto = param.buildAjaxSystemUserList();
		McUserInfoView userCache = param.getUserCache();
		if(userCache.getType().equals("leader") ) {     // master.getType() will be: leader or admin or user
			dto.setType("'leader','admin'");
			dto.setCid(null); 				// 联合查询字段主动置空 防御攻击
			dto.setPlatform(null);	
		}else {
			dto.setType(" 'user' ");   // admin用户不应该显示在其对应的平台的用户列表中
			dto.setPlatform(userCache.getWebcode()); 		// 后续like查询
		}
		
		int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = dto.getStartIndex();
				pageSize = dto.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<McUserInfoView> list = mcUserInfoMapper.findPageList(dto);
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<McUserInfoView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}
	
	/**
	 * @description: 添加系统类型用户：leader 或 admin。
	 * 		针对user类型用户由于涉及到mc_user_info_ext记录，迁移到matrix-employee模块进行操作
	 *		
	 * @author Yangcl
	 * @date 2019年12月5日 上午10:28:56 
	 * @version 1.0.0.1|update in v1.6.1.6-multiple-jspweb
	 */
	public Result<?> addSysUser(@Valid AddMcUserInfoRequest param) {
		Result<?> validate = param.validate(mcUserInfoMapper);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			McUserInfo entity = param.buildAddSysUser();
			int count = mcUserInfoMapper.insertSelectiveGetZid(entity);
			if(count == 1){	// 100010102=数据添加成功!
				return Result.SUCCESS(this.getInfo(100010102));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);
	}
	
	/**
	 * @description: 修改用户信息
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 下午2:28:38 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> editSysUser(@Valid UpdateMcUserInfoRequest param) {
		Result<?> validate = param.validateEditSysUser();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			McUserInfo find = mcUserInfoMapper.find(param.getId());		// 此处不判空，抛异常数据回滚。
			McUserInfo e = param.buildEditSysUser();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1){
				launch.loadDictCache(CachePrefix.UserInfoNp , null).del(find.getUserName() + "," + find.getPassword());
				return Result.SUCCESS(this.getInfo(100010104));
			}
			return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);
		} catch (Exception ex) {
			ex.printStackTrace();		// 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
	}
	
	/**
	 * @description: 修改用户密码
	 *
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxPasswordReset(@Valid UpdateMcUserInfoPasswordRequest param) {
		Result<?> validate = param.validateAjaxPasswordReset();
		if(validate.getStatus().equals("error")) {
			return validate;
		}

		try {
			McUserInfo e = param.buildAjaxPasswordReset();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1) {
				launch.loadDictCache(CachePrefix.UserInfoNp , null).del(param.getUserName() + "," + param.getOldPassWord());
				return Result.SUCCESS(this.getInfo(101010008));		// 101010008=密码更新成功
			}
			return Result.ERROR(this.getInfo(101010038), ResultCode.OPERATION_FAILED);		// 101010038=用户密码重置失败
		} catch (Exception ex) {
			ex.printStackTrace();		// 101010038=用户密码重置失败   100010112=服务器异常!
			throw new RuntimeException(this.getInfo(101010038) + "，" + this.getInfo(100010112));
		}
	}
	
	/**
	 * @descriptions 删除一个用户|不保留数据库中的记录
	 *
	 * @param id
	 * @date 2017年5月19日 上午12:04:28
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> deleteUser(@Valid DeleteMcUserInfoRequest param) {
		Long id = param.getId();
		McUserInfoView view = param.getUserCache();
		try {
			int count = mcUserInfoMapper.deleteById(id);
			if(count != 1) {		// 100010124=数据删除失败，数据库连接异常!
				return Result.ERROR(this.getInfo(100010124), ResultCode.ERROR_DELETE);
			}
			
			List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(id);
			if(CollectionUtils.isNotEmpty(list)) {
				int count_ = mcUserRoleMapper.deleteByUserId(id); // 确定该用户有角色被分配才去删除
				if(count_ != 1) {
					throw new RuntimeException(this.getInfo(100010105));
				}
			}
			
			String[] arr = view.getPlatform().split(",");
			if(arr != null && arr.length != 0) {
				for(String platform : arr) {
					launch.loadDictCache(CachePrefix.McUserRole , null).del(platform + "@" + id);
				}
			}
			launch.loadDictCache(CachePrefix.UserInfoNp , null).del(view.getUserName() + "," + view.getPassword());
			return Result.SUCCESS(this.getInfo(100010106));		// 100010106=数据删除成功!
		} catch (Exception ex) {
			ex.printStackTrace();		 // 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
	}
	
	/**
	 * @description: 重新加载系统用户缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnUserCacheReload() {
		try {
			launch.loadDictCache(CachePrefix.McSysFunc , null).batchDeleteByPrefix("");
			launch.loadDictCache(CachePrefix.McRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(CachePrefix.McUserRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(CachePrefix.UserInfoNp , null).batchDeleteByPrefix("");
			return Result.SUCCESS(this.getInfo(101010011));	// 101010011=系统字典缓存刷新完成!
		} catch (Exception ex) {
			ex.printStackTrace();		 // 100020112=系统错误, 请联系开发人员!
			throw new RuntimeException(this.getInfo(100020112));
		}
	}
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////【仅项目使用】////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	public Result<ClientLoginView> ajaxClientLogin(@Valid FindLoginRequest param, HttpServletRequest request) {
		Result<ClientLoginView> validate = param.validateAjaxClientLogin();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		String userInfoNpJson = launch.loadDictCache(CachePrefix.UserInfoNp , "UserInfoNpInit").get(param.getUserName() + "," + SignUtil.md5Sign(param.getPassword()));
		if (StringUtils.isBlank(userInfoNpJson)) {		// 101010017=用户名或密码错误
			return Result.ERROR(this.getInfo(101010017), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		if(StringUtils.isBlank(info.getPlatform()) || !StringUtils.contains(info.getPlatform(), param.getPlatform())) {	// 101010023=未授权用户，平台未对您分配权限，标识码：{0}
			return Result.ERROR(this.getInfo(101010023 , param.getPlatform()), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		String pageJson = launch.loadDictCache(CachePrefix.McUserRole , "McUserRoleInit").get(param.getPlatform() + "@" + info.getId().toString());
		if(StringUtils.isBlank(pageJson)) {  	// 101010022=未授权用户
			return Result.ERROR(this.getInfo(101010022), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		info.setWebcode(param.getPlatform());
		
		// launch.loadDictCache(DCacheEnum.AccessToken , null).get(dto.getAccessToken());  token获取为空，则用户登录已经超时，需要重新登录
		String accessToken = SignUtil.md5Sign(param.getUserName()) + SignUtil.md5Sign(String.valueOf(System.currentTimeMillis()));
		launch.loadDictCache(CachePrefix.AccessToken , null).set(accessToken , JSONObject.toJSONString(info) , 15*24*60*60);		// 设置用户令牌，有效时间15天
		
		ClientLoginView view = new ClientLoginView();
		view.setAccessToken(accessToken);
		view.setInfo(userInfoNpJson);
		view.setPageJson(pageJson);
		view.setUploadUrl(this.getConfig("matrix-core.ajax_file_upload_" + this.getConfig("matrix-core.model")));	// 系统文件上传
		return Result.SUCCESS(view);
	}
	
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		退出系统登录|客户端用户：nodejs/IOS平板等
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2018年10月10日 下午7:29:38 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxClientLogout(@Valid FindLogoutRequest param) {
		launch.loadDictCache(CachePrefix.AccessToken , null).del(param.getAccessToken());
		return Result.SUCCESS( this.getInfo(101010015));  // 101010015=系统已经退出
	}
	
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		获取用户详情
	 *
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	public Result<McUserInfoView> ajaxFindSysUser(@Valid FindMcUserInfoRequest param) {
		McUserInfo entity = mcUserInfoMapper.find(param.getId());
		if(entity == null){		// 101010032=获取用户详情失败，状态查询异常
			return Result.ERROR(this.getInfo(101010032), ResultCode.NOT_FOUND);
		}
		
		String userInfoNpJson = launch.loadDictCache(CachePrefix.UserInfoNp , "UserInfoNpInit").get(entity.getUserName() + "," + entity.getPassword());
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		return Result.SUCCESS(this.getInfo(100010100), info);	// 100010100=数据请求成功!
	}







}





























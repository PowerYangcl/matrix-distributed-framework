package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.matrix.pojo.request.AddMcUserInfoRequest;
import com.matrix.pojo.request.DeleteMcUserInfoRequest;
import com.matrix.pojo.request.FindLoginRequest;
import com.matrix.pojo.request.FindLogoutRequest;
import com.matrix.pojo.request.FindMcUserInfoListRequest;
import com.matrix.pojo.request.FindMcUserInfoRequest;
import com.matrix.pojo.request.UpdateMcUserInfoRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.BaseView;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcOrganizationMapper;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McSysFunction;
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
 * @version 1.0.0
 */
@Service("mcUserInfo") 
public class McUserInfoServiceImpl extends BaseServiceImpl<Long , McUserInfo , McUserInfoDto , BaseView> implements IMcUserInfoService{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IMcUserInfoMapper mcUserInfoMapper;
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper;
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;
	@Resource
	private IMcOrganizationMapper mcOrganizationMapper;

	/**
	 * @description: 验证用户登录信息|PC端用户|【仅JSP项目使用】
	 * 
	 * @author Yangcl 
	 * @date 2016年11月25日 下午4:17:47 
	 * @version 1.0.0.1
	 */
	public Result<LoginView> login(FindLoginRequest param , HttpSession session) {
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(param.getUserName() + "," + SignUtil.md5Sign(param.getPassword()));
		if (StringUtils.isBlank(userInfoNpJson)) {		// 101010017=用户名或密码错误
			return Result.ERROR(this.getInfo(101010017), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString());
		if(StringUtils.isBlank(info.getPlatform()) || StringUtils.isBlank(pageJson)) {  	// 101010022=未授权用户
			return Result.ERROR(this.getInfo(101010022), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		if(!info.getPlatform().equals(param.getPlatform())) {		// 101010023=未授权用户，平台未对您分配权限，标识码：{0}
			return Result.ERROR(this.getInfo(101010023 , param.getPlatform()), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		McUserRoleCache cache = JSONObject.parseObject(pageJson, McUserRoleCache.class);
		List<McSysFunction> msfList = new ArrayList<McSysFunction>();
		for(McSysFunction m : cache.getMsfList()) {	// 防止误操作，去掉与标识码无关的功能项
			if(m.getPlatform() != null && m.getPlatform().equals(param.getPlatform())) {
				msfList.add(m);
			}
		}
		cache.setMsfList(msfList); 
		
		session.setAttribute("userInfo", info);   // 写入session
		
		LoginView view = new LoginView();
		view.setPageJson(JSONObject.toJSONString(cache));
		view.setInfo(userInfoNpJson);
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
	public Result<PageInfo<McUserInfoView>> ajaxSystemUserList(FindMcUserInfoListRequest param , HttpServletRequest request) {
		Result<PageInfo<McUserInfoView>> validate = param.validateAjaxSystemUserList();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		McUserInfoDto dto = param.buildAjaxSystemUserList();
		McUserInfoView userCache = param.getUserCache();
		if(userCache.getType().equals("leader") ) {     // master.getType() will be: leader or admin or user
			dto.setType("'leader','admin'");
			dto.setCid(null); 		// 联合查询字段主动置空 防御攻击
			dto.setPlatform(null);	
		}else {
			dto.setType(" 'user' ");   // 非Leader平台的Admin用户不应该显示在其对应的平台的用户列表中
			dto.setCid(userCache.getCid());
			dto.setPlatform(userCache.getPlatform()); 
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
	 * @description: 添加用户
	 *
	 * @author Yangcl
	 * @date 2019年12月5日 上午10:28:56 
	 * @version 1.0.0.1
	 */
	public Result<?> addSysUser(AddMcUserInfoRequest param) {
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
	public Result<?> editSysUser(UpdateMcUserInfoRequest param) {
		Result<?> validate = param.validateEditSysUser();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			McUserInfo find = mcUserInfoMapper.find(param.getId());		// 此处不判空，抛异常数据回滚。
			McUserInfo e = param.buildEditSysUser();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1){
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(find.getUserName() + "," + find.getPassword());
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
	public Result<?> ajaxPasswordReset(UpdateMcUserInfoRequest param) {
		Result<?> validate = param.validateAjaxPasswordReset();
		if(validate.getStatus().equals("error")) {
			return validate;
		}

		try {
			McUserInfo e = param.buildAjaxPasswordReset();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1) {
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(param.getUserName() + "," + param.getOldPassWord());
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
	public Result<?> deleteUser(DeleteMcUserInfoRequest param) {
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
			
			launch.loadDictCache(DCacheEnum.McUserRole , null).del(id.toString());
			launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(view.getUserName() + "," + view.getPassword());
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
			launch.loadDictCache(DCacheEnum.McSysFunc , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.McRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.McUserRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.UserInfoNp , null).batchDeleteByPrefix("");
			return Result.SUCCESS(this.getInfo(101010011));	// 101010011=系统字典缓存刷新完成!
		} catch (Exception ex) {
			ex.printStackTrace();		 // 100020112=系统错误, 请联系开发人员!
			throw new RuntimeException(this.getInfo(100020112));
		}
	}
	
	/**
	 * @description: 获取平台信息列表|ManagerCenterController使用
	 *
	 * @param info
	 * @author Yangcl
	 * @date 2018年9月22日 下午2:23:23 
	 * @version 1.0.0.1
	 */
	public Result<List<McSysFunction>> ajaxPlatformInfoList() {
		try {
			McSysFunction e = new McSysFunction();
			e.setNavType(0);
			e.setAuthorize(0); 		// 用户与角色是否委托Leader创建。0:委托 1:不委托|nav_type=0此字段生效。
			List<McSysFunction> sflist = mcSysFunctionMapper.getSysFuncList(e);
			return Result.SUCCESS(sflist);
		} catch (Exception ex) {
			ex.printStackTrace(); 		// 101010044=获取平台信息列表失败，状态查询异常
			return Result.ERROR(this.getInfo(101010044), ResultCode.SERVER_EXCEPTION);
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
	public Result<ClientLoginView> ajaxClientLogin(FindLoginRequest param, HttpServletRequest request) {
		Result<ClientLoginView> validate = param.validateAjaxClientLogin();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(param.getUserName() + "," + SignUtil.md5Sign(param.getPassword()));
		if (StringUtils.isBlank(userInfoNpJson)) {		// 101010017=用户名或密码错误
			return Result.ERROR(this.getInfo(101010017), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString());
		if(StringUtils.isBlank(info.getPlatform()) || StringUtils.isBlank(pageJson)) {  	// 101010022=未授权用户
			return Result.ERROR(this.getInfo(101010022), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		if(!info.getPlatform().equals(param.getPlatform())) {		// 101010023=未授权用户，平台未对您分配权限，标识码：{0}
			return Result.ERROR(this.getInfo(101010023 , param.getPlatform()), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		
		McUserRoleCache cache = JSONObject.parseObject(pageJson, McUserRoleCache.class);
		List<McSysFunction> msfList = new ArrayList<McSysFunction>();
		for(McSysFunction m : cache.getMsfList()) {	// 防止误操作，去掉与标识码无关的功能项
			if(m.getPlatform().equals(param.getPlatform())) {
				msfList.add(m);
			}
		}
		cache.setMsfList(msfList); 
		
		// launch.loadDictCache(DCacheEnum.AccessToken , null).get(dto.getAccessToken());  token获取为空，则用户登录已经超时，需要重新登录
		String accessToken = SignUtil.md5Sign(param.getUserName()) + SignUtil.md5Sign(String.valueOf(System.currentTimeMillis()));
		launch.loadDictCache(DCacheEnum.AccessToken , null).set(accessToken , userInfoNpJson , 60*60);		// 设置用户令牌，有效时间1小时
		
		ClientLoginView view = new ClientLoginView();
		view.setAccessToken(accessToken);
		view.setPageJson(JSONObject.toJSONString(cache));
		view.setInfo(userInfoNpJson);
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
	public Result<?> ajaxClientLogout(FindLogoutRequest param) {
		launch.loadDictCache(DCacheEnum.AccessToken , null).del(param.getAccessToken());
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
	public Result<McUserInfoView> ajaxFindSysUser(FindMcUserInfoRequest param) {
		McUserInfo entity = mcUserInfoMapper.find(param.getId());
		if(entity == null){		// 101010032=获取用户详情失败，状态查询异常
			return Result.ERROR(this.getInfo(101010032), ResultCode.NOT_FOUND);
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(entity.getUserName() + "," + entity.getPassword());
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		return Result.SUCCESS(this.getInfo(100010100), info);	// 100010100=数据请求成功!
	}







}





























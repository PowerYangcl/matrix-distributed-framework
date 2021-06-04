package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.matrix.dao.*;
import com.matrix.pojo.entity.*;
import com.matrix.pojo.request.AddMcUserInfoRequest;
import com.matrix.pojo.request.FindLoginRequest;
import com.matrix.pojo.request.FindMcUserInfoRequest;
import com.matrix.pojo.request.UpdateMcUserInfoRequest;

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
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.view.LoginView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
import com.matrix.support.ValidateCodeSupport;
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
		if (StringUtils.isBlank(param.getUserName()) || StringUtils.isBlank(param.getPassword())) {
			// 101010001=用户名或密码不得为空
			return Result.ERROR(this.getInfo(101010001), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(param.getPlatform())) {		// 101010002=平台唯一标识码不得为空
			return Result.ERROR(this.getInfo(101010002), ResultCode.MISSING_ARGUMENT);
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
	public Result<?> logout(HttpSession session) {// 101010015=系统已经退出
		return Result.SUCCESS( this.getInfo(101010015));
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
	public Result<PageInfo<McUserInfoView>> ajaxSystemUserList(FindMcUserInfoRequest param , HttpServletRequest request) {
		McUserInfoDto dto = param.buildAjaxSystemUserList();
		McUserInfoView userCache = param.getUserCache();
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString()  , userCache.getType() )) {   
			// 用户会话异常! platform cod or cid is null
			return Result.ERROR(this.getInfo(101010013), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
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
		Result<?> validate = param.validateAddSysUser();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		try {
			McUserInfo e = new McUserInfo();
			e.setUserName(param.getUserName());
			List<McUserInfo> list = mcUserInfoMapper.queryPage(e);
			if(list != null && list.size() != 0) { 		// 101010029=用户名已存在
				return Result.ERROR(this.getInfo(101010029), ResultCode.INTERNAL_VALIDATION_FAILED);
			}
			
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
			if( !param.getUserName().equals(param.getUserNameOld()) ) {	// 开始用户名是否重复
				McUserInfoDto dto = new McUserInfoDto();
				dto.setUserName(param.getUserName());
				McUserInfo ishas = mcUserInfoMapper.findEntityByDto(dto);
				if(ishas != null) {		 // 101010029=用户名已存在
					return Result.ERROR(this.getInfo(101010029), ResultCode.INTERNAL_VALIDATION_FAILED);
				}
			}
			McUserInfo e = param.buildEditSysUser();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1){
				McUserInfo find = mcUserInfoMapper.find(e.getId());		// 此处不判空，抛异常数据回滚。
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
		McUserInfo user = mcUserInfoMapper.find(param.getId());
		if(user == null) {			// 101010038=用户密码重置失败，未找到指定用户，请重试
			return Result.ERROR(this.getInfo(101010038), ResultCode.RESULT_NULL);
		}
		if (!user.getPassword().equals(SignUtil.md5Sign(param.getOldPassWord()))){
			// 101010042=用户密码重置失败，原始密码不正确
			return Result.ERROR(this.getInfo(101010042), ResultCode.INTERNAL_VALIDATION_FAILED);
		}
		
		try {
			McUserInfo e = param.buildAjaxPasswordReset();
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1) {
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(user.getUserName() + "," + user.getPassword());
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
	public JSONObject deleteUser(McUserInfoDto dto) {
		JSONObject result = new JSONObject();
		Long id = dto.getId();
		if(StringUtils.isBlank(id.toString())){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010031)); // 101010031=页面数据错误,用户id为空
			return result;
		}
		try {
			McUserInfoView view = dto.getUserCache(); // (McUserInfoView) session.getAttribute("userInfo");  // mcUserInfoMapper.loadUserInfo(id);
			int count = mcUserInfoMapper.deleteById(id);   
			int count_ = 1;
			if(StringUtils.isNotBlank(launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(id.toString()))) {
				count_ = mcUserRoleMapper.deleteByUserId(id); // 确定该用户有角色被分配才去删除
			}
			if(count == 1 && count_ == 1){
				launch.loadDictCache(DCacheEnum.McUserRole , null).del(id.toString());
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(view.getUserName() + "," + view.getPassword());
				result.put("status", "success");
				result.put("msg", this.getInfo(101010001)); // 101010001=删除成功
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(101010002));  // 101010002=删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));	// 101010026=服务器异常
		}
		
		return result;
	}
	
	/**
	 * @description: 重新加载系统用户缓存
	 *
	 * @author Yangcl
	 * @date 2019年12月10日 下午3:49:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnUserCacheReload() {
		JSONObject result = new JSONObject();
		try {
			launch.loadDictCache(DCacheEnum.McSysFunc , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.McRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.McUserRole , null).batchDeleteByPrefix("");
			launch.loadDictCache(DCacheEnum.UserInfoNp , null).batchDeleteByPrefix("");
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010008)); // 系统异常
			return result;
		}
		
		result.put("status", "success");
		result.put("msg", this.getInfo(101010011)); // 系统字典缓存刷新完成!
		return result;
	}
	
	/**
	 * @description: 获取平台信息列表|ManagerCenterController使用
	 *
	 * @param info
	 * @author Yangcl
	 * @date 2018年9月22日 下午2:23:23 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPlatformInfoList() {
		JSONObject result = new JSONObject();
		try {
			McSysFunction e = new McSysFunction();
			e.setNavType(0);
			e.setAuthorize(0); 		// 用户与角色是否委托Leader创建。0:委托 1:不委托|nav_type=0此字段生效。
			List<McSysFunction> sflist = mcSysFunctionMapper.getSysFuncList(e);
			result.put("status", "success");
			result.put("sflist", sflist);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010032));	// 101010044=获取平台信息列表失败，状态查询异常
		}
		return result;
	}
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////【仅项目使用】////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		验证用户登录信息|客户端用户：nodejs/IOS平板等
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年10月10日 上午10:51:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxClientLogin(HttpServletRequest request, McUserInfoDto dto) {
		JSONObject result = new JSONObject();
		result.put("uploadUrl", this.getConfig("matrix-core.ajax_file_upload_" + this.getConfig("matrix-core.model")));  // 系统文件上传
		if (StringUtils.isBlank(dto.getUserName()) || StringUtils.isBlank(dto.getPassword())) {
			result.put("status", "error");
			result.put("msg", "用户名或密码不得为空");
			return result;
		}
		if(StringUtils.isBlank(dto.getPlatform())) {
			result.put("status", "error");
			result.put("msg", "平台唯一标识码不得为空");
			return result;
		}
		
		
		if(this.getConfig("matrix-core.model").equals("master")) {   // 线上环境验证码生效
			if(StringUtils.isBlank(dto.getValidateCode())) {
				result.put("status", "error");
				result.put("msg", "验证码不得为空");
				return result;
			}
			if(StringUtils.isBlank(dto.getValidateCodeKey())) {
				result.put("status", "error");
				result.put("msg", "验证码Key不能为空");
				return result;
			}
			String validateCode = ValidateCodeSupport.getCode(dto.getValidateCodeKey());
			if(StringUtils.isBlank(validateCode)) {
				result.put("status", "error");
				result.put("msg", "验证码已过期");
				return result;
			}else {
				if(!validateCode.equalsIgnoreCase(dto.getValidateCode())) {
					result.put("status", "error");
					result.put("msg", "验证码错误");
					return result;
				}
			}
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(dto.getUserName() + "," + SignUtil.md5Sign(dto.getPassword()));
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		if (StringUtils.isNotBlank(userInfoNpJson) && info != null) { 
			String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(info.getId().toString());
			if(StringUtils.isBlank(info.getPlatform()) || StringUtils.isBlank(pageJson)) { 
				result.put("status", "error");
				result.put("msg", "未授权用户");
				return result;
			}
			if( !info.getPlatform().equals(dto.getPlatform()) ) {
				result.put("status", "error");
				result.put("msg", "未授权用户，平台未对您分配权限，标识码：" + dto.getPlatform());
				return result;
			}
			
			McUserRoleCache cache = JSONObject.parseObject(pageJson, McUserRoleCache.class);
			List<McSysFunction> msfList = new ArrayList<McSysFunction>();
			for(McSysFunction m : cache.getMsfList()) {	// 防止误操作，去掉与标识码无关的功能项
				if(m.getPlatform().equals(dto.getPlatform())) {
					msfList.add(m);
				}
			}
		    cache.setMsfList(msfList); 
		    
		    // launch.loadDictCache(DCacheEnum.AccessToken , null).get(dto.getAccessToken());  token获取为空，则用户登录已经超时，需要重新登录；故不需要Init****相关类
		    String accessToken = SignUtil.md5Sign(dto.getUserName()) + SignUtil.md5Sign(String.valueOf(System.currentTimeMillis()));
			launch.loadDictCache(DCacheEnum.AccessToken , null).set(accessToken , userInfoNpJson , 60*60);		// 设置用户令牌，有效时间1小时
		    
			result.put("accessToken", accessToken);
			result.put("data" , JSONObject.toJSONString(cache));    
			result.put("info", userInfoNpJson); 
			result.put("status", "success");
			result.put("msg", this.getInfo(101010016));  // 101010016=调用成功   // TODO  改
			return result;
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010017));  // 101010017=用户名或密码错误
			return result;
		}
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
	public JSONObject ajaxClientLogout(McUserInfoDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getAccessToken())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010014));  // 101010014=用户令牌(accessToken)为空
			return result;
		}
		
		launch.loadDictCache(DCacheEnum.AccessToken , null).del(dto.getAccessToken());
		result.put("status", "success");
		result.put("msg", this.getInfo(101010015));  // 101010015=系统已经退出
		return result;
	}

	
	/**
	 * @description: 【仅matrix-manager-api项目使用】
	 * 		获取用户详情
	 *
	 * @param dto
	 * @param session 
	 * @author Yangcl
	 * @date 2018年10月12日 下午7:42:13 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindSysUser(McUserInfoDto dto) {
		JSONObject result = new JSONObject();
		if (dto.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010030));	// 101010030=获取用户详情失败，用户id为空
			return result;
		}
		McUserInfo entity = mcUserInfoMapper.find(dto.getId());
		if(entity == null){      
			result.put("status", "error");
			result.put("msg", this.getInfo(101010030));	// 101010030=获取用户详情失败，用户id为空
			return result;
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(entity.getUserName() + "," + entity.getPassword());
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		result.put("status", "success");
		result.put("msg", this.getInfo(101010014));		// 101010014=查询成功
		result.put("entity", info);
		return result;
	}







}





























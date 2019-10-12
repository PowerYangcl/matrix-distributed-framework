package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.matrix.dao.*;
import com.matrix.pojo.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.BaseView;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
import com.matrix.support.ValidateCodeSupport;
import com.matrix.util.SignUtil;


/**
 * @description: 后台用户登录、退出等等操作
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
	 * @description: 用户登录操作
	 * 
	 * @param dto
	 * @param session
	 * @author Yangcl 
	 * @date 2016年11月25日 下午3:51:36 
	 * @version 1.0.0.1
	 */
	public JSONObject login(McUserInfoDto dto , HttpSession session) {
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
				if(m.getPlatform() != null && m.getPlatform().equals(dto.getPlatform())) {
					msfList.add(m);
				}
			}
		    cache.setMsfList(msfList); 
		    
		    session.setAttribute("userInfo", info);   // 写入session
			result.put("data" , JSONObject.toJSONString(cache));    
			result.put("info", userInfoNpJson); 
			result.put("status", "success");
			result.put("msg", "调用成功");
			return result;
		} else {
			result.put("status", "error");
			result.put("msg", "用户名或密码错误");
			return result;
		}
	}
	
	
	
	/**
	 * @description: 验证用户登录信息|客户端用户：nodejs/IOS平板等
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
			result.put("msg", this.getInfo(400010016));  // 400010016=调用成功
			return result;
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010017));  // 400010017=用户名或密码错误
			return result;
		}
	}
	
	
	
	/**
	 * @descriptions 退出登录
	 *
	 * @date 2017年5月25日 下午10:48:50
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject logout(HttpSession session) {
		JSONObject result = new JSONObject();
		session.removeAttribute("userInfo"); 
		result.put("status", "success");
		result.put("msg", this.getInfo(400010015));  // 400010015=系统已经退出
		return result;
	}
	
	/**
	 * @description: 退出系统登录|客户端用户：nodejs/IOS平板等
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
			result.put("msg", this.getInfo(400010014));  // 400010014=用户令牌(accessToken)为空
			return result;
		}
		
		launch.loadDictCache(DCacheEnum.AccessToken , null).del(dto.getAccessToken());
		result.put("status", "success");
		result.put("msg", this.getInfo(400010015));  // 400010015=系统已经退出
		return result;
	}

	
	public JSONObject addSysUser(McUserInfoDto info) {
		JSONObject result = new JSONObject();
		McUserInfoView userCache = info.getUserCache(); //(McUserInfoView) session.getAttribute("userInfo");
		if (StringUtils.isBlank(info.getUserName()) || StringUtils.isBlank(info.getPassword())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010019));	// 400010019=用户名或密码不得为空
			return result;
		}
		if (StringUtils.isBlank(info.getMobile())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010020)); // 400010020=用户手机号码不得为空
			return result;
		}
		if (StringUtils.isBlank(info.getEmail())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010021));  // 400010021=用户电子邮箱不得为空
			return result;
		}
		if (userCache.getType().equals("leader") && StringUtils.isBlank(info.getPlatform())) {   // 当非Leader后台调用的时候，这里不得为空，可以随意传入一个字符串，但后面代码不会使用
			result.put("status", "error");
			result.put("msg", this.getInfo(400010018)); // 400010018=平台识别码错误
			return result;
		}
		McUserInfo entity = new McUserInfo();
		entity.setUserName(info.getUserName());
		List<McUserInfo> queryPage = mcUserInfoMapper.queryPage(entity);
		if(queryPage != null && queryPage.size() != 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010029)); // 400010029=用户名已存在
			return result;
		}
		
		
		
		McUserInfo e = new McUserInfo();
		e.setUserName(info.getUserName());
		e.setPassword(SignUtil.md5Sign(info.getPassword()));
		if(StringUtils.contains(info.getPlatform(), "@")) { // leader Leader后台用户|admin 其他平台管理员|user其他平台用户
			e.setType(info.getPlatform().split("@")[0]);
			e.setCid(info.getCid()); 		// Leader后台创建的admin类型用户
		}else {
			e.setType("user"); // 标识其他平台管理员所创建的用户
			e.setCid(userCache.getCid()); 		// 继承其创建者的cid
			e.setUserCode(String.valueOf(System.currentTimeMillis()).substring(5, 13));
			if(info.getMcOrganizationId() == null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(400010036)); // 400010036=请选择组织机构
				return result;
			}
			e.setMcOrganizationId(info.getMcOrganizationId());
			e.setQq(info.getQq());
		}
		e.setMobile(info.getMobile());
		e.setEmail(info.getEmail());
		e.setSex(info.getSex());  
		e.setRemark(info.getRemark());
		e.setIdcard(info.getIdcard());
		
		if(StringUtils.contains(info.getPlatform(), "@")) { // Leader平台传入的标识码会有 'leader@' + code (Leader平台用户)或者 'admin@' + code的情况(其他平台管理员由Leader创建);
			e.setPlatform(info.getPlatform().split("@")[1]);
		}else {
			e.setPlatform(userCache.getPlatform()); 		// 非Leader平台(具体某个平台)创建的用户，则继承其创建者的平台标识码			 
		}
		
		e.setCreateTime(new Date());
		e.setCreateUserId(userCache.getId());
		e.setCreateUserName(userCache.getUserName()); 
		e.setUpdateTime(new Date());
		e.setUpdateUserId(userCache.getId());
		e.setUpdateUserName(userCache.getUserName()); 
		try {
			int count = mcUserInfoMapper.insertSelectiveGetZid(e);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(400010022));	// 400010022=添加成功
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010023));	// 400010023=添加失败
			}
		} catch (Exception e_) {
			e_.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(400010026));	// 400010026=服务器异常
		}
		return result;
	}
	
	
	/**
	 * @description: 获取用户详情
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
			result.put("msg", this.getInfo(400010030));	// 400010030=获取用户详情失败，用户id为空
			return result;
		}
		McUserInfo entity = mcUserInfoMapper.find(dto.getId());
		if(entity == null){      
			result.put("status", "error");
			result.put("msg", this.getInfo(400010030));	// 400010030=获取用户详情失败，用户id为空
			return result;
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(entity.getUserName() + "," + entity.getPassword());
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		result.put("status", "success");
		result.put("msg", this.getInfo(400010014));		// 400010014=查询成功
		result.put("entity", info);
		return result;
	}


	public JSONObject editSysUser(McUserInfoDto info) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(info.getUserName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010019));  // 400010019=用户名或密码不得为空
			return result;
		}
		if (StringUtils.isBlank(info.getMobile())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010020));	// 400010020=用户手机号码不得为空
			return result;
		}
		if (StringUtils.isBlank(info.getEmail())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010021));		// 400010021=用户电子邮箱不得为空
			return result;
		}
		if( !info.getUserName().equals(info.getUserNameOld()) ) {	// 开始用户名是否重复
			McUserInfoDto dto_ = new McUserInfoDto();
			dto_.setUserName(info.getUserName());
			McUserInfo ishas = mcUserInfoMapper.findEntityByDto(dto_);
			if(ishas != null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(400010029));		// 400010029=用户名已存在
				return result;
			}
		}
		
		try {
			McUserInfoView master = info.getUserCache();  // (McUserInfoView) session.getAttribute("userInfo");
			McUserInfo e = new McUserInfo();
			e.setId(info.getId()); 
			e.setUserName(info.getUserName());
			if(StringUtils.isNotBlank(info.getPassword())) {
				e.setPassword(SignUtil.md5Sign(info.getPassword()));
			}
			e.setMobile(info.getMobile());
			e.setEmail(info.getEmail());
			e.setSex(info.getSex());   
			e.setIdcard(info.getIdcard());
			e.setUpdateTime(new Date());
			e.setUpdateUserId(master.getId());
			e.setUpdateUserName(master.getUserName()); 
			e.setQq(info.getQq());
			e.setRemark(info.getRemark());
			e.setPicUrl(info.getPicUrl());			// 更新用户头像
			// Leader平台传入的标识码会有 'leader@' + code (Leader平台用户)或者 'admin@' + code的情况(其他平台管理员由Leader创建);
			if(master.getType().equals("leader") && StringUtils.isNotBlank(info.getPlatform()) && StringUtils.contains(info.getPlatform(), "@")) { 
				e.setPlatform(info.getPlatform().split("@")[1]);	// 非Leader平台创建的用户，不会更新平台码
			} 
			int count = mcUserInfoMapper.updateSelective(e);
			if(count == 1){
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(info.getUserName() + "," + info.getPassword());
//				launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(info.getUserName() + "," + info.getPassword());
				result.put("status", "success");
				result.put("msg", this.getInfo(400010024));	// 400010024=更新成功
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010025));  // 400010025=更新失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(400010026));	// 400010026=服务器异常
		}
		return result;
	}
	
	/**
	 * @description: 修改用户密码
	 *
	 * @param info 
	 * @author Yangcl
	 * @date 2018年10月29日 上午11:05:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPasswordReset(McUserInfoDto info) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(info.getPassword()) || info.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010037));  // 400010037=用户密码不得为空
			return result;
		}
		
		McUserInfo user = mcUserInfoMapper.find(info.getId());
		if(user == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010038));  // 400010038=用户密码重置失败
			return result;
		}
		//判断原始密码是否相等  msh 21018 12 19
		if(StringUtils.isNotBlank(info.getOldPassWord())){
			if (user.getPassword().equals(SignUtil.md5Sign(info.getOldPassWord()))){

			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010042));  // 400010042=原始密码不正确
				return result;
			}
		}
		
		McUserInfoView master = info.getUserCache(); 
		McUserInfo e = new McUserInfo();
		e.setId(info.getId()); 
		e.setPassword(SignUtil.md5Sign(info.getPassword())); 
		e.setUpdateTime(new Date());
		e.setUpdateUserId(master.getId());
		e.setUpdateUserName(master.getUserName()); 
		int count = mcUserInfoMapper.updateSelective(e);
		if(count != 1) {
			result.put("status", "error");
			result.put("msg", this.getInfo(400010038));  // 400010038=用户密码重置失败
			return result;
		}
		
		launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(user.getUserName() + "," + user.getPassword());
		launch.loadDictCache(DCacheEnum.UserInfoNp , "UserInfoNpInit").get(user.getUserName() + "," + info.getPassword());  // 缓存重置

		result.put("status", "success");
		result.put("msg", this.getInfo(400010024));	// 400010024=更新成功
		return result;
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
			result.put("msg", this.getInfo(400010031)); // 400010031=页面数据错误,用户id为空
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
				result.put("msg", this.getInfo(400010001)); // 400010001=删除成功
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010002));  // 400010002=删除失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(400010026));	// 400010026=服务器异常
		}
		
		return result;
	}

	public JSONObject updatePageStyle(McUserInfoDto dto) {
		JSONObject result = new JSONObject();
		McUserInfoView master = dto.getUserCache();
		McUserInfo e = new McUserInfo();
		e.setId(dto.getId());
		e.setPageCss(dto.getPageCss()); 
		e.setUpdateTime(new Date());
		e.setUpdateUserId(master.getId());
		e.setUpdateUserName(master.getUserName()); 
		mcUserInfoMapper.updateSelective(e);
		
		McUserInfoView view = mcUserInfoMapper.loadUserInfo(dto.getId());
		launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(view.getUserName() + "," + view.getPassword() , JSONObject.toJSONString(view) , 30*24*60*60);
		
		result.put("status", "success");
		result.put("msg", this.getInfo(400010024));	// 400010024=更新成功
		return result;
	}

	/**
	 * @description: 绘制添加用户界面
	 *
	 * @param info
	 * @author Yangcl
	 * @date 2018年9月22日 下午2:23:23 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxDrawAddUserPage() {
		JSONObject result = new JSONObject();
		try {
			McSysFunction e = new McSysFunction();
			e.setNavType(0);
			List<McSysFunction> sflist = mcSysFunctionMapper.getSysFuncList(e);
			result.put("status", "success");
			result.put("sflist", sflist);
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(400010032));	// 400010032=获取用户详情失败，状态查询异常
		}
		return result;
	}

	/**
	 * @description: 系统用户列表数据|非Leader平台的Admin用户不应该显示在其对应的平台的用户列表中
	 *
	 *		sysUserList.jsp根据Leader系统或者mip-web等衍生系统的不同
	 *		增加dto.type 和 dto.platform为参数条件
	 *		而cid则需要从session获取
	 *
	 *
	 *
	 * @param dto.type：and i.type in(${type}) |Leader系统只有dto.type作为查询条件
	 * 	 dto.cid  				
	 * 	 dto.platform
	 * 	 dto.mcOrganizationId|从组织机构树拿到，查询该机构下的平台用户信息|非Leader平台会出现
	 * 
	 * @author Yangcl
	 * @date 2018年9月23日 上午12:03:05 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxSystemUserList(McUserInfoDto dto , HttpServletRequest request) {
		McUserInfoView userCache = dto.getUserCache();
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString()  , userCache.getType() )) {   
			JSONObject r = new JSONObject();
			r.put("status", "error");
			r.put("msg", this.getInfo(400010013));   // 用户会话异常! platform cod or cid is null
			return r;
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
		
		return super.pageListByDto(dto, request);
	}



}





























package com.matrix.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleFunctionMapper;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.dto.McUserRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McRoleFunction;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.request.AddMcRoleRequest;
import com.matrix.pojo.request.DeleteMcRoleRequest;
import com.matrix.pojo.request.FindMcRoleRequest;
import com.matrix.pojo.request.FindUserRoleListRequest;
import com.matrix.pojo.request.UpdateMcRoleRequest;
import com.matrix.pojo.request.UpdateMcRoleTreeRequest;
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.McRoleView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcRoleService;

/**
 * @description: 系统角色支撑
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月18日 下午3:48:30 
 * @version 1.0.0.1
 */
@Service("mcRoleService") 
public class McRoleServiceImpl extends BaseServiceImpl<Long , McRole , McRoleDto , McRoleView> implements IMcRoleService {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Resource
	private IMcRoleMapper mcRoleMapper;
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper;
	@Resource
	private IMcRoleFunctionMapper mcRoleFunctionMapper;
	
	
	/**
	 * @description: 系统角色列表数据
	 *
	 * @author Yangcl
	 * @date 2018年9月24日 下午3:58:48 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<McRoleView>> ajaxSystemRoleList(FindMcRoleRequest param, HttpServletRequest request){
		McUserInfoView userCache = param.getUserCache();
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString() , userCache.getType())) {   
			// 用户会话异常! platform cod or cid is null
			return Result.ERROR(this.getInfo(101010013), ResultCode.INVALID_ARGUMENT);
		}
		
		McRoleDto dto = param.buildAjaxSystemRoleList();
		// 此种情况为Leader后台进行数据请求 且对别当前登录用户的缓存信息是否正确
		if(userCache.getType().equals("leader") ) {     // master.getType() will be: leader or admin or user
			dto.setType("leader");
			dto.setCid(null); 		// 联合查询字段主动置空 防御攻击
			dto.setPlatform(null);	
		}else {
			dto.setType("admin");   // 由具体某个平台的系统管理员所创建
			dto.setCid(userCache.getCid());
			dto.setPlatform(userCache.getPlatform()); 
		}
		return super.pageListByDto(dto, request); 
	}
	
	/**
	 * @description: 添加一个角色，不勾选系统功能 | ajax_btn_开头【此接口需要验证用户按钮权限】
	 * 
	 * @author Yangcl 
	 * @date 2017年5月19日 下午9:10:56 
	 * @version 1.0.0.1
	 */
	public Result<?> addMcRole(AddMcRoleRequest param) {
		McRole role = param.buildAddMcRole();
		if(StringUtils.isBlank(role.getRoleName())){ // 101010027=角色名称不得为空
			return Result.ERROR(this.getInfo(101010027), ResultCode.MISSING_ARGUMENT);
		}
		McUserInfoView userCache = role.getUserCache();
		if(!userCache.getType().equals("leader") && StringUtils.isNotBlank(role.getPlatform()) ) {
			// 101010028=非Leader平台用户创建角色不得携带平台编码
			return Result.ERROR(this.getInfo(101010028), ResultCode.ILLEGAL_ARGUMENT);
		}
		
		if(userCache.getType().equals("leader") ) {
			role.setType("leader");
		}else {
			// 其他平台系统管理员或拥有分配权限的用户创建的角色，类型固定为admin
			role.setType("admin");
			// leader后台创建的角色会传入平台码(如：133C9CB27E18)|其他平台则默认使用用户所在平台的数据
			role.setPlatform(userCache.getPlatform());	
		}
		role.setCid(userCache.getCid());
		role.buildAddCommon(userCache);
		
		try {
			// 验证角色名称是否重复
			McRole role_ = new McRole();
			role_.setCid(role.getCid());
			role_.setPlatform(role.getPlatform());
			role_.setRoleName(role.getRoleName());
			McRole ishas = mcRoleMapper.findByType(role_);
			if(ishas != null) {  // 101010041=角色名称已经存在
				return Result.ERROR(this.getInfo(101010041), ResultCode.ALREADY_EXISTS);
			}
			
			int count = mcRoleMapper.insertGotEntityId(role); 
			if(count == 1){
				launch.loadDictCache(DCacheEnum.McRole , null).del(role.getId().toString());
				return Result.SUCCESS(this.getInfo(100010102));	// 100010102=数据添加成功!
			}
			return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);	// 100010103=数据添加失败，服务器异常!
		} catch (Exception ex) {
			ex.printStackTrace(); // 101010026=服务器异常
			throw new RuntimeException(this.getInfo(101010026));
		}
	}
	
	/**
	 * @descriptions 修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Result<?> editSysRole(UpdateMcRoleRequest param) {
		if(StringUtils.isBlank(param.getRoleName())){  // 101010027=角色名称不得为空
			return Result.ERROR(this.getInfo(101010027), ResultCode.MISSING_ARGUMENT);
		}
		if(param.getId() == null){ 
			return Result.ERROR(this.getInfo(100020103), ResultCode.MISSING_ARGUMENT);
		}
		
		McUserInfoView userCache = param.getUserCache();
		try {
			if(!param.getRoleName().equals(param.getOldRoleName())) {
				// 验证角色名称是否重复
				McRole role_ = new McRole();
				role_.setCid(userCache.getCid());
				if(userCache.getType().equals("leader") ) {
					role_.setPlatform(param.getPlatform());
				}else {
					role_.setPlatform(userCache.getPlatform());
				}
				role_.setRoleName(param.getRoleName());
				McRole ishas = mcRoleMapper.findByType(role_);
				if(ishas != null) {  // 101010041=角色名称已经存在
					return Result.ERROR(this.getInfo(101010041), ResultCode.ALREADY_EXISTS);
				}
			}
			
			McRole e = param.buildEditSysRole(); 
			int count = mcRoleMapper.updateSelective(e);
			if(count == 1){
				String mcRole = launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(param.getId().toString());
				String ids = "";
				if(StringUtils.isNotBlank(mcRole)){
					ids = JSONObject.parseObject( mcRole ).getString("ids");  
				}
				launch.loadDictCache(DCacheEnum.McRole , null).del(param.getId().toString());
				return Result.SUCCESS(this.getInfo(100010104));	// 100010104=数据更新成功!
			}
			return Result.ERROR(this.getInfo(101010004), ResultCode.ERROR_UPDATE);	// 系统角色创建失败
		} catch (Exception ex) {
			ex.printStackTrace(); // 100010112=服务器异常!
			return Result.ERROR(this.getInfo(100010112), ResultCode.SERVER_EXCEPTION);
		}
	}
	
	/**
	 * @description: 删除系统角色|系统权限配置 / 系统用户相关 / 系统角色列表
	 * 							 判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> deleteMcRole(DeleteMcRoleRequest param) {
		try {
			if(mcUserRoleMapper.selectByMcRoleId(param.getMcRoleId()).size() != 0){ 
				// 该角色已经关联了用户，如果想删除则必选先将用户与该角色解除绑定
				return Result.ERROR(this.getInfo(101010009), ResultCode.OPERATION_FAILED);
			}
			McUserInfoView userCache = param.getUserCache();
			McRole entity = mcRoleMapper.find(param.getMcRoleId());
			if(!userCache.getType().equals("leader") && !userCache.getPlatform().equals(entity.getPlatform())) {
				// 101010057=非leader用户无权限删除其他平台角色
				return Result.ERROR(this.getInfo(101010057), ResultCode.OPERATION_FAILED);
			}
			
			mcRoleMapper.deleteById(param.getMcRoleId());
			mcRoleFunctionMapper.deleteByMcRoleId(param.getMcRoleId()); 
			launch.loadDictCache(DCacheEnum.McRole , null).del(param.getMcRoleId().toString());  
			return Result.SUCCESS(this.getInfo(100010106));	// 100010106=数据删除成功!
		} catch (Exception ex) {
			ex.printStackTrace(); // 101010006=系统角色删除失败
			throw new RuntimeException(this.getInfo(101010006));
		}
	}
	
	/**
	 * @description: 修改角色所关联的系统功能|【角色列表】->【角色功能】->【提交】按钮
	 * 							 在系统功能树(ztree)中勾选选中的功能点与这个角色进行关联
	 * @param d
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<McRoleCache> editMcRole(UpdateMcRoleTreeRequest param) {
		if(StringUtils.isBlank(param.getIds())){ // 101010003=请勾选系统功能
			return Result.ERROR(this.getInfo(101010003), ResultCode.MISSING_ARGUMENT);
		}
		
		McUserInfoView userInfo = param.getUserCache(); 
		McRole role = new McRole();
		role.setId(param.getMcRoleId()); 
		role.buildUpdateCommon(userInfo);
		try {
			mcRoleMapper.updateSelective(role);	// 仅修改更新时间
			mcRoleFunctionMapper.deleteByMcRoleId(param.getMcRoleId()); 
			launch.loadDictCache(DCacheEnum.McRole , null).del(param.getMcRoleId().toString());  
			String[] arr = param.getIds().split(",");
			for(int i = 0 ; i < arr.length ; i ++){
				McRoleFunction rf = new McRoleFunction();
				rf.setMcRoleId(role.getId());
				rf.setMcSysFunctionId(Long.valueOf(arr[i])); 
				rf.buildAddCommon(userInfo);
				mcRoleFunctionMapper.insertSelective(rf);
			}
			McRoleCache c = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(param.getMcRoleId().toString()), McRoleCache.class);
			return Result.SUCCESS(this.getInfo(101010045), c);  // 101010045=系统角色与系统功能绑定成功!
		} catch (Exception ex) {
			ex.printStackTrace(); // 101010005=系统角色修改失败
			throw new RuntimeException(this.getInfo(101010005));
		}
	}
	
	/**
	 * @description: 在系统功能树(ztree)中解绑与这个角色关联的功能点|【角色列表】->【角色功能】->【解绑】按钮|TODO 尚未在matrix-manager-api中添加类
	 *								 
	 * @param dto.mcRoleId
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> ajaxBtnRelieveMcRole(UpdateMcRoleTreeRequest param) {
		McUserInfoView userInfo = param.getUserCache(); 
		McRole role = new McRole();
		role.setId(param.getMcRoleId()); 
		role.buildUpdateCommon(userInfo);
		try {
			mcRoleMapper.updateSelective(role);
			mcRoleFunctionMapper.deleteByMcRoleId(param.getMcRoleId()); 
			launch.loadDictCache(DCacheEnum.McRole , null).del(param.getMcRoleId().toString());  
			return Result.SUCCESS(this.getInfo(101010046));  // 101010046=系统角色与系统功能解绑成功!
		} catch (Exception ex) {
			ex.printStackTrace();  // 系统角色修改失败
			throw new RuntimeException(this.getInfo(101010005));
		}
	}
	
	/**
	 * @description: 展示权限列表|如果用户没有这个权限了则标识为【分配】，如果已经有了这个按钮则标识位【取消】
	 * 		系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发的弹框中显示的列表
	 *
	 * @author Yangcl
	 * @date 2019年12月16日 下午4:15:53 
	 * @version 1.0.0.1
	 */
	public Result<PageInfo<McRoleView>> userRoleList(FindUserRoleListRequest param , HttpServletRequest request) {
		McRoleDto dto = param.buildUserRoleList();
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
			
			McUserInfoView userCache = dto.getUserCache();
			if(userCache.getType().equals("leader") ) {     // master.getType() will be: leader or admin or user
				dto.setType("leader"); // + dto.platform取结果集
			}else {
				dto.setType("admin");   // 由具体某个平台的系统管理员所创建
				dto.setCid(userCache.getCid());
				dto.setPlatform(userCache.getPlatform()); 
			}
			
			List<McRoleView> list = mcRoleMapper.queryPageView(dto);
			if (list != null && list.size() > 0) {
				List<McUserRole> urList = mcUserRoleMapper.selectByMcUserId(param.getUserId());  
				if(urList != null && urList.size() != 0){
					for(int i = 0 ; i < list.size() ; i ++){
						for(McUserRole ur : urList){
							if(list.get(i).getId().longValue() == ur.getMcRoleId().longValue()){
								list.get(i).setUserId(ur.getMcUserId()); 
							}
						}
					}
				}
			}
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<McRoleView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}
	
	/**
	 * @description: 给指定用户分配一个角色
	 * 		系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【分配】按钮
	 *
	 * @param entity.mcRoleId
	 * @param entity.mcUserId
	 *  
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:30:40 
	 * @version 1.0.0.1
	 */
	public JSONObject addUserRole(McUserRole e) {
		JSONObject result = new JSONObject();
		Date createTime = new Date();
		McUserInfoView userInfo = e.getUserCache();
		e.setRemark("");
		e.setCreateTime(createTime);
		e.setCreateUserId(userInfo.getId());
		e.setCreateUserName(userInfo.getUserName());
		e.setUpdateTime(createTime);
		e.setUpdateUserId(userInfo.getId());
		e.setUpdateUserName(userInfo.getUserName());
		try {
			Integer count = mcUserRoleMapper.insertSelective(e);
			if(count != 0){
				result.put("status", "success");
				result.put("msg", this.getInfo(101010056)); // 101010056=系统角色分配成功
				// 实例化缓存   
				this.reloadUserFunction(e.getMcUserId());  
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(101010007)); // 101010007=系统角色分配失败
			}
		} catch (Exception e2) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010008)); // 101010008=系统异常
		}
		return result;
	}
	
	/**
	 * @description: 解除角色绑定，同时删除缓存
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发弹框列表/【取消】按钮
	 *
	 * @param dto.userId
	 * @param dto.mcRoleId
	 *  
	 * @author Yangcl
	 * @date 2019年12月17日 下午5:39:55 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteUserRole(McUserRoleDto d) {
		JSONObject result = new JSONObject();
		try {
			if(d.getUserId() == null ||d.getMcRoleId() == null){
				result.put("status", "error");
				result.put("msg", "页面数据信息不全"); // 页面数据信息不全  
				return result;
			}
			
			mcUserRoleMapper.deleteByDto(d);   
			this.reloadUserFunction(d.getUserId()); 
			
			result.put("status", "success");
			result.put("msg", this.getInfo(101010010)); // 系统角色移除成功! 
		} catch (Exception e) {
			e.printStackTrace(); 
			result.put("status", "error");
			result.put("msg", this.getInfo(101010008)); // 系统异常
		}
		return result;
	}
	
	

	

	
	
	
	
	
	
	
	
	
	/**
	 * @description: 角色详情【仅matrix-manager-api项目使用】
	 *
	 * @param info.id mc_role表自增id
	 * 
	 * @author Yangcl
	 * @date 2018年10月13日 下午3:37:05 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindRoleInfo(McRole e) {
		JSONObject result = new JSONObject();
		if(e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010033));	// 101010033=角色id不得为空
			return result;
		}
		McRole entity = mcRoleMapper.find(e.getId());
		if(entity == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010034));	// 101010034=后台数据查询失败
			return result;
		}
		
		result.put("status", "success");
		result.put("msg", this.getInfo(101010014));	// 101010014=查询成功
		result.put("entity", entity);
		return result;
	}
	 
	
	
	
	
	
	/**
	 * @description: 实例化用户功能缓存   
	 * 
	 * @param userId
	 * @author Yangcl 
	 * @date 2017年5月24日 下午3:05:35 
	 * @version 1.0.0.1
	 */
	private void reloadUserFunction(Long userId){
		launch.loadDictCache(DCacheEnum.McUserRole , null).del(userId.toString()); 
		McUserRoleCache cache = new McUserRoleCache();
		cache.setMcUserId(userId);
		List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(userId);
		if(list != null && list.size() != 0){
			Set<Long> set = new TreeSet<Long>();  
			for(McUserRole r : list){
				String roleJson = launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(r.getMcRoleId().toString());
				if(StringUtils.isNotBlank(roleJson)){
					McRoleCache role = JSONObject.parseObject(roleJson, McRoleCache.class);
					if(role == null){
						continue;
					}
					if(StringUtils.isNotBlank(role.getIds())){
						String [] arr = role.getIds().split(",");
						for(String s : arr){
							set.add(Long.valueOf(s)); 
						}
					}
				}
			}
			if(set != null && set.size() != 0){
				for(Long id : set){
					String rfJson = launch.loadDictCache(DCacheEnum.McSysFunc , "McSysFuncInit").get(id.toString());
					if(StringUtils.isNotBlank(rfJson)){
						McSysFunction rf = JSONObject.parseObject(rfJson, McSysFunction.class);
						if(rf == null){
							continue;
						}
						cache.getMsfList().add(rf); 
					}
				}
			}
			launch.loadDictCache(DCacheEnum.McUserRole , null).set(userId.toString() , JSONObject.toJSONString(cache)  , 30*24*60*60); 
		}
	}
}






























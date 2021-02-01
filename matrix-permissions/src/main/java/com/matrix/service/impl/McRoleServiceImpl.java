package com.matrix.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.RpcResultCode;
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
	 * @param dto 
	 * 
	 * @author Yangcl
	 * @date 2018年9月24日 下午3:58:48 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxSystemRoleList(McRoleDto dto, HttpServletRequest request){
		McUserInfoView userCache = dto.getUserCache();
		if(StringUtils.isAnyBlank(userCache.getPlatform() , userCache.getCid().toString() , userCache.getType())) {   
			JSONObject r = new JSONObject();
			r.put("status", "error");
			r.put("msg", this.getInfo(101010013));   // 用户会话异常! platform cod or cid is null
			return r;
		}
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
	public JSONObject addMcRole(McRole role) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(role.getRoleName())){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010027));  // 101010027=角色名称不得为空
			return result;
		}
		McUserInfoView userCache = role.getUserCache();
		if(!userCache.getType().equals("leader") && StringUtils.isNotBlank(role.getPlatform()) ) {
			result.put("status", "error");
			result.put("msg", this.getInfo(101010028)); // 101010028=非Leader平台用户创建角色不得携带平台编码
			return result;
		}
		if(userCache.getType().equals("leader") ) {
			role.setType("leader");
		}else {
			role.setType("admin"); // 其他平台系统管理员或拥有分配权限的用户创建的角色，类型固定为admin
			role.setPlatform(userCache.getPlatform());	// leader后台创建的角色会传入平台码(如：133C9CB27E18 or 133EFB922DF3)|其他平台则默认使用用户
		}
		role.setCid(userCache.getCid());
		
		role.setRemark("");
		Date createTime = new Date();
		role.setCreateTime(createTime);
		role.setCreateUserId(userCache.getId());
		role.setCreateUserName(userCache.getUserName());
		role.setUpdateTime(createTime);
		role.setUpdateUserId(userCache.getId());
		role.setUpdateUserName(userCache.getUserName()); 
		try {
			// 验证角色名称是否重复
			McRole role_ = new McRole();
			role_.setCid(role.getCid());
			role_.setPlatform(role.getPlatform());
			role_.setRoleName(role.getRoleName());
			McRole ishas = mcRoleMapper.findByType(role_);
			if(ishas != null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(101010041));	// 101010041=角色名称已经存在
				return result;
			}
			
			int count = mcRoleMapper.insertGotEntityId(role); 
			if(count == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(101010022));			// 101010022=添加成功
				McRoleCache c = new McRoleCache();
				c.setMcRoleId(role.getId());
				c.setRoleName(role.getRoleName());
				c.setRoleDesc(role.getRoleDesc());               
				c.setCid(role.getCid());
				c.setType(role.getType());
				c.setPlatform(role.getPlatform()); 
				launch.loadDictCache(DCacheEnum.McRole , null).set(role.getId().toString() , JSONObject.toJSONString(c) , 30*24*60*60);   
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(101010004));	// 101010004=系统角色创建失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026)); // 101010026=服务器异常
		}
		return result;
	}
	
	/**
	 * @descriptions 修改角色名称和描述，不勾选系统功能|角色编辑页面的提交按钮
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject editSysRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getRoleName())){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010027));		// 101010027=角色名称不得为空
			return result;
		}
		if(dto.getId() == null){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010025));		// 101010025=更新失败
			return result;
		}
		Date currentTime = new Date();
		McUserInfoView userInfo = dto.getUserCache();
		try {
			if(!dto.getRoleName().equals(dto.getOldRoleName())) {
				// 验证角色名称是否重复
				McRole role_ = new McRole();
				role_.setCid(userInfo.getCid());
				if(userInfo.getType().equals("leader") ) {
					role_.setPlatform(dto.getPlatform());
				}else {
					role_.setPlatform(userInfo.getPlatform());
				}
				role_.setRoleName(dto.getRoleName());
				McRole ishas = mcRoleMapper.findByType(role_);
				if(ishas != null) {
					result.put("status", "error");
					result.put("msg", this.getInfo(101010041));	// 101010041=角色名称已经存在
					return result;
				}
			}
			
			McRole e = new McRole();
			e.setId(dto.getId());
			e.setRoleName(dto.getRoleName());
			e.setRoleDesc(dto.getRoleDesc());
			e.setUpdateTime(currentTime);
			e.setUpdateUserId(userInfo.getId());
			e.setUpdateUserName(userInfo.getUserName());
			
			int count = mcRoleMapper.updateSelective(e);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(101010024));		// 101010024=更新成功
				McRoleCache c = new McRoleCache();
				c.setMcRoleId(dto.getId());
				c.setRoleName(dto.getRoleName());
				c.setRoleDesc(dto.getRoleDesc());        
				String mcRole = launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(dto.getId().toString());
				String ids = "";
				if(StringUtils.isNotBlank(mcRole)){
					ids = JSONObject.parseObject( mcRole ).getString("ids");  
				}
				c.setIds(ids); 
				launch.loadDictCache(DCacheEnum.McRole , null).del(dto.getId().toString());  
				launch.loadDictCache(DCacheEnum.McRole , null).set(dto.getId().toString() , JSONObject.toJSONString(c) , 30*24*60*60); 
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(101010004));	// 系统角色创建失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010026));	// 101010026=服务器异常
		}
		return result;
	}
	
	/**
	 * @description: 删除系统角色|系统权限配置 / 系统用户相关 / 系统角色列表
	 * 							 判断 mc_user_role 表中是否已经关联了用户，如果关联了，则不允许删除
	 * @param dto
	 * @author Yangcl 
	 * @date 2017年4月20日 上午11:02:30 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteMcRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		try {
			if(mcUserRoleMapper.selectByMcRoleId(dto.getMcRoleId()).size() != 0){ 
				result.put("status", "error");
				result.put("msg", this.getInfo(101010009)); // 该角色已经关联了用户，如果想删除则必选先将用户与该角色解除绑定
			}else{
				mcRoleMapper.deleteById(dto.getMcRoleId());
				mcRoleFunctionMapper.deleteByMcRoleId(dto.getMcRoleId()); 
				launch.loadDictCache(DCacheEnum.McRole , null).del(dto.getMcRoleId().toString());  
				result.put("status", "success");
				result.put("msg", this.getInfo(101010001)); // 101010001=删除成功
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010006)); // 系统角色删除失败
		}
		
		return result;
	}
	
	/**
	 * @description: 修改角色所关联的系统功能|【角色列表】->【角色功能】->【提交】按钮
	 * 							 在系统功能树(ztree)中勾选选中的功能点与这个角色进行关联
	 * @param d
	 * @author Yangcl 
	 * @date 2017年4月19日 下午4:22:28 
	 * @version 1.0.0.1
	 */
	public JSONObject editMcRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getIds())){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010003)); // 请勾选系统功能
		}else{
			Date currentTime = new Date();
			McUserInfoView userInfo = dto.getUserCache(); 
			McRole role = new McRole();
			role.setId(dto.getMcRoleId()); 
			role.setUpdateTime(currentTime); 
			role.setUpdateUserId(userInfo.getId());
			role.setUpdateUserName(userInfo.getUserName()); 
			try {
				mcRoleMapper.updateSelective(role);
				mcRoleFunctionMapper.deleteByMcRoleId(dto.getMcRoleId()); 
				launch.loadDictCache(DCacheEnum.McRole , null).del(dto.getMcRoleId().toString());  
				String[] arr = dto.getIds().split(",");
				for(int i = 0 ; i < arr.length ; i ++){
					McRoleFunction rf = new McRoleFunction();
					rf.setMcRoleId(role.getId());
					rf.setMcSysFunctionId(Long.valueOf(arr[i])); 
					rf.setDeleteFlag(1);
					rf.setRemark("");
					rf.setCreateTime(currentTime);
					rf.setCreateUserId(userInfo.getId());
					rf.setCreateUserName(userInfo.getUserName());
					rf.setUpdateTime(currentTime);
					rf.setUpdateUserId(userInfo.getId());
					rf.setUpdateUserName(userInfo.getUserName());
					mcRoleFunctionMapper.insertSelective(rf);
				}
				McRoleCache c = JSONObject.parseObject(launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(dto.getMcRoleId().toString()), McRoleCache.class);
				
				result.put("status", "success");
				result.put("msg" , this.getInfo(101010045));    // 101010045=系统角色与系统功能绑定成功!
				result.put("cache", c);
			} catch (Exception e) {
				e.printStackTrace();
				result.put("status", "error");
				result.put("msg", this.getInfo(101010005)); // 系统角色修改失败
			}
		}
		return result;
	}
	
	/**
	 * @description: 在系统功能树(ztree)中解绑与这个角色关联的功能点|【角色列表】->【角色功能】->【解绑】按钮|TODO 尚未在matrix-manager-api中添加类
	 *								 
	 * @param dto
	 * @author Yangcl
	 * @date 2019年11月20日 下午3:41:54 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnRelieveMcRole(McRoleDto dto) {
		JSONObject result = new JSONObject();
		
		McUserInfoView userInfo = dto.getUserCache(); 
		McRole role = new McRole();
		role.setId(dto.getMcRoleId()); 
		role.setUpdateTime(new Date()); 
		role.setUpdateUserId(userInfo.getId());
		role.setUpdateUserName(userInfo.getUserName()); 
		try {
			mcRoleMapper.updateSelective(role);
			mcRoleFunctionMapper.deleteByMcRoleId(dto.getMcRoleId()); 
			launch.loadDictCache(DCacheEnum.McRole , null).del(dto.getMcRoleId().toString());  
			
			result.put("status", "success");
			result.put("msg" , this.getInfo(101010046));    // 101010046=系统角色与系统功能解绑成功!
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(101010005)); // 系统角色修改失败
		}
		
		return result;
	}
	
	/**
	 * @description: 展示权限列表|如果用户没有这个权限了则标识为【分配】，如果已经有了这个按钮则标识位【取消】
	 * 
	 * 	系统权限配置 / 系统用户相关 / 系统用户列表-【用户角色】按钮所触发的弹框中显示的列表
	 *
	 * @param role.userId
	 * @param role.platform
	 * 
	 * @author Yangcl
	 * @date 2019年12月16日 下午4:15:53 
	 * @version 1.0.0.1
	 */
	public JSONObject userRoleList(McRoleDto dto , HttpServletRequest request) {
		JSONObject result = new JSONObject();
		String pageNum = request.getParameter("pageNum"); // 当前第几页
		String pageSize = request.getParameter("pageSize"); // 当前页所显示记录条数
		int num = 1;
		int size = 10;
		if (StringUtils.isNotBlank(pageNum)) {
			num = Integer.parseInt(pageNum);
		}
		if (StringUtils.isNotBlank(pageSize)) {
			size = Integer.parseInt(pageSize);
		}
		PageHelper.startPage(num, size);
		
		McUserInfoView userCache = dto.getUserCache();
		if(userCache.getType().equals("leader") ) {     // master.getType() will be: leader or admin or user
			dto.setType("leader"); // + dto.platform取结果集
		}else {
			dto.setType("admin");   // 由具体某个平台的系统管理员所创建
			dto.setCid(userCache.getCid());
			dto.setPlatform(userCache.getPlatform()); 
		}
		
		try {
			List<McRoleView> list = mcRoleMapper.queryPageView(dto);
			result.put("status", "success");
			if (list != null && list.size() > 0) {
				List<McUserRole> urList = mcUserRoleMapper.selectByMcUserId(dto.getUserId());  
				if(urList != null && urList.size() != 0){
					for(int i = 0 ; i < list.size() ; i ++){
						for(McUserRole ur : urList){
							if(list.get(i).getId().longValue() == ur.getMcRoleId().longValue()){
								list.get(i).setUserId(ur.getMcUserId()); 
							}
						}
					}
				}
				result.put("code" , RpcResultCode.SUCCESS);
			}else {
				result.put("code" , RpcResultCode.RESULT_NULL);
				result.put("msg", this.getInfo(100010115));  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
			PageInfo<McRoleView> pageList = new PageInfo<McRoleView>(list);
			result.put("data", pageList);
			result.put("entity", dto);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(100010116));  // 100010116=分页数据返回失败，服务器异常!
			return result;
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






























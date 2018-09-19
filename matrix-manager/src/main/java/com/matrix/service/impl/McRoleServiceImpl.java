package com.matrix.service.impl;

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
	 * @description:返回列表数据，格式化时间 
	 * 
	 * @param e
	 * @param request
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月19日 下午2:33:24 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPageData(McRole e , HttpServletRequest request){
		JSONObject r = super.ajaxPageData(e, request);
//		if(r.getString("status").equals("success")){											dev_standardisation_refactor_180724	此版本删除这段代码
//			JSONObject data = r.getJSONObject("data");					
//			JSONArray list = new JSONArray();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			for(int i = 0 ; i < data.getJSONArray("list").size() ; i ++){
//				Date ctime = data.getJSONArray("list").getJSONObject(i).getDate("createTime");
//				String date = sdf.format(ctime); 
//				JSONObject o = data.getJSONArray("list").getJSONObject(i);
//				o.put("createTime", date);
//				list.add(o);
//			}  
//			data.put("list", list); 
//			r.put("data", data); 
//		}
		return r;
	}

	
	@Override
	public JSONObject addMcRole(McRole role , HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(role.getRoleName())){
			result.put("status", "error");
			result.put("msg", "角色名称不得为空");
			return result;
		}
//		Date createTime = new Date();
		McUserInfoView userInfo = (McUserInfoView) session.getAttribute("userInfo");
//		role.setFlag(1);
//		role.setCreateTime(createTime);
//		role.setUpdateTime(createTime);
		role.setRemark("");
		role.setCreateUserId(userInfo.getId());
		role.setCreateUserName(userInfo.getUserName());
		role.setUpdateUserId(userInfo.getId());
		role.setUpdateUserName(userInfo.getUserName()); 
		try {
			int count = mcRoleMapper.insertGotEntityId(role); 
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "添加成功");
				McRoleCache c = new McRoleCache();
				c.setMcRoleId(role.getId());
				c.setRoleName(role.getRoleName());
				c.setRoleDesc(role.getRoleDesc());               // 添加时候 缓存信息不完整
				launch.loadDictCache(DCacheEnum.McRole , null).set(role.getId().toString() , JSONObject.toJSONString(c));   
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010004));	// 系统角色创建失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "服务器异常");
		}
		return result;
	}


	/**
	 * @descriptions 修改角色名称和描述
	 *
	 * @date 2017年5月21日 下午1:37:10
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	public JSONObject editSysRole(McRole role, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(role.getRoleName())){
			result.put("status", "error");
			result.put("msg", "角色名称不得为空");
			return result;
		}
		if(role.getId() == null){
			result.put("status", "error");
			result.put("msg", "页面数据错误，更新失败");
			return result;
		}
		Date currentTime = new Date();
		McUserInfoView userInfo = (McUserInfoView) session.getAttribute("userInfo");
		role.setUpdateTime(currentTime);
		role.setUpdateUserId(userInfo.getId());
		try {
			int count = mcRoleMapper.updateSelective(role);
			if(count == 1){
				result.put("status", "success");
				result.put("msg", "修改成功");
				McRoleCache c = new McRoleCache();
				c.setMcRoleId(role.getId());
				c.setRoleName(role.getRoleName());
				c.setRoleDesc(role.getRoleDesc());        
				String mcRole = launch.loadDictCache(DCacheEnum.McRole , "InitMcRole").get(role.getId().toString());
				String ids = "";
				if(StringUtils.isNotBlank(mcRole)){
					ids = JSONObject.parseObject( mcRole ).getString("ids");  
				}
				c.setIds(ids); 
				launch.loadDictCache(DCacheEnum.McRole , null).del(role.getId().toString());  
				launch.loadDictCache(DCacheEnum.McRole , null).set(role.getId().toString() , JSONObject.toJSONString(c)); 
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(400010004));	// 系统角色创建失败
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "服务器异常");
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
		List<McRoleView> list = mcRoleMapper.queryPageView(null);
		if (list != null && list.size() > 0) {
			List<McUserRole> urList = mcUserRoleMapper.selectByMcUserId(dto.getUserId());  
			if(urList != null && urList.size() != 0){
				for(int i = 0 ; i < list.size() ; i ++){
					for(McUserRole ur : urList){
						if(list.get(i).getId() == ur.getMcRoleId()){
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






























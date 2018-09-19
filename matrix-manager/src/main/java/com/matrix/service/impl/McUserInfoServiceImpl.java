package com.matrix.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.BaseView;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserInfoExtMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserInfoExt;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IMcUserInfoService;
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
	private IMcUserInfoExtMapper mcUserInfoExtMapper;
	
	/**
	 * @description: 用户登录操作
	 * 
	 * @param userInfo
	 * @param session
	 * @author Yangcl 
	 * @date 2016年11月25日 下午3:51:36 
	 * @version 1.0.0.1
	 */
	public JSONObject login(McUserInfo userInfo , HttpSession session) {
		JSONObject result = new JSONObject();
		result.put("uploadUrl", this.getConfig("matrix-core.ajax_file_upload_" + this.getConfig("matrix-core.model")));  // 系统文件上传
		if (StringUtils.isBlank(userInfo.getUserName()) || StringUtils.isBlank(userInfo.getPassword())) {
			result.put("status", "error");
			result.put("msg", "用户名或密码不得为空");
			return result;
		}
		
		String userInfoNpJson = launch.loadDictCache(DCacheEnum.UserInfoNp , "InitUserInfoNp").get(userInfo.getUserName() + SignUtil.md5Sign(userInfo.getPassword()));
		McUserInfoView info = JSONObject.parseObject(userInfoNpJson, McUserInfoView.class);
		if (StringUtils.isNotBlank(userInfoNpJson) && info != null) { 
			session.setAttribute("userInfo", info);   // 写入session
		    String pageJson = launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(info.getId().toString());
			
			result.put("data" , pageJson);  
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
		result.put("msg", "调用成功");
		return result;
	}

	/**
	 * @description: 格式化创建时间  
	 *  
	 * @author Yangcl 
	 * @date 2017年5月19日 下午4:59:20 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxPageData(McUserInfo e , HttpServletRequest request){
		JSONObject r = super.ajaxPageData(e, request);
		if(r.getString("status").equals("success")){
			JSONObject data = r.getJSONObject("data");
			JSONArray list = new JSONArray();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for(int i = 0 ; i < data.getJSONArray("list").size() ; i ++){
				Date ctime = data.getJSONArray("list").getJSONObject(i).getDate("createTime");
				String date = sdf.format(ctime); 
				JSONObject o = data.getJSONArray("list").getJSONObject(i);
				o.put("createTime", date);
				list.add(o);
			}  
			data.put("list", list); 
			r.put("data", data); 
		}
		return r;
	}
	
	public JSONObject addSysUser(McUserInfo info) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(info.getUserName()) || StringUtils.isBlank(info.getPassword())) {
			result.put("status", "error");
			result.put("msg", "用户名或密码不得为空");
			return result;
		}
		if (StringUtils.isBlank(info.getMobile())) {
			result.put("status", "error");
			result.put("msg", "用户手机号码不得为空");
			return result;
		}
		if (StringUtils.isBlank(info.getEmail())) {
			result.put("status", "error");
			result.put("msg", "用户电子邮箱不得为空");
			return result;
		}
		info.setPassword(SignUtil.md5Sign(info.getPassword()));   
		info.setIdcard("");
		info.setCreateTime(new Date());
		info.setRemark("admin create this user"); 
		
		try {
			int count = mcUserInfoMapper.insertSelectiveGetZid(info);
			if(count == 1){
				McUserInfoExt e = new McUserInfoExt();
				e.setUserInfoId(info.getId()); 
				mcUserInfoExtMapper.insertSelective(e);
				
				McUserInfoView view = mcUserInfoMapper.loadUserInfo(info.getId());
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(view.getUserName()+view.getPassword() , JSONObject.toJSONString(view));
				
				result.put("status", "success");
				result.put("msg", "添加成功");
			}else{
				result.put("status", "error");
				result.put("msg", "添加失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "服务器异常");
		}
		
		
		return result;
	}


	public JSONObject editSysUser(McUserInfo info , HttpSession session) {
		JSONObject result = new JSONObject();
		if (StringUtils.isBlank(info.getUserName()) || StringUtils.isBlank(info.getPassword())) {
			result.put("status", "error");
			result.put("msg", "用户名或密码不得为空");
			return result;
		}
		if (StringUtils.isBlank(info.getMobile())) {
			result.put("status", "error");
			result.put("msg", "用户手机号码不得为空");
			return result;
		}
		if (StringUtils.isBlank(info.getEmail())) {
			result.put("status", "error");
			result.put("msg", "用户电子邮箱不得为空");
			return result;
		}
		info.setPassword(SignUtil.md5Sign(info.getPassword()));    
		info.setRemark("admin edit this user"); 
		
		try {
			McUserInfoView userInfo = (McUserInfoView) session.getAttribute("userInfo");
			info.setUpdateTime(new Date());
			info.setUpdateUserId(userInfo.getId());
			info.setUpdateUserName(userInfo.getUserName()); 
//			McUserInfoView view = mcUserInfoMapper.loadUserInfo(info.getId());
			
			launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(userInfo.getUserName() + userInfo.getPassword());
			int count = mcUserInfoMapper.updateSelective(info);
			if(count == 1){
				// TODO 以后会更新mc_user_info_ext表
				McUserInfoView view = mcUserInfoMapper.loadUserInfo(info.getId());
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(view.getUserName() + view.getPassword() , JSONObject.toJSONString(view));
				result.put("status", "success");
				result.put("msg", "修改成功");
			}else{
				result.put("status", "error");
				result.put("msg", "修改失败");  
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "服务器异常");
		}
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
	public JSONObject deleteUser(Long id , HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(id.toString())){
			result.put("status", "error");
			result.put("msg", "页面数据错误,用户id为空");
			return result;
		}
		try {
			McUserInfoView view = (McUserInfoView) session.getAttribute("userInfo");  // mcUserInfoMapper.loadUserInfo(id);
			int count = mcUserInfoMapper.deleteById(id);   
			int count_ = 1;
			if(StringUtils.isNotBlank(launch.loadDictCache(DCacheEnum.McUserRole , "InitMcUserRole").get(id.toString()))) {
				count_ = mcUserRoleMapper.deleteByUserId(id); // 确定该用户有角色被分配才去删除
			}
			int cout__ = mcUserInfoExtMapper.deleteByUserId(id);  // 删除mc_user_info_ext表的用户扩展信息 
			if(count == 1 && count_ == 1 && cout__ == 1){
				launch.loadDictCache(DCacheEnum.McUserRole , null).del(id.toString());
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).del(view.getUserName() + view.getPassword());
				result.put("status", "success");
				result.put("msg", "删除成功");
			}else{
				result.put("status", "error");
				result.put("msg", "删除失败");  
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", "服务器异常");
		}
		
		return result;
	}

	@Override
	public JSONObject updatePageStyle(McUserInfoView dto) {
		McUserInfoExt e = new McUserInfoExt();
		e.setUserInfoId(dto.getId());
		e.setPageCss(dto.getPageCss()); 
		mcUserInfoExtMapper.updateSelectiveByUserId(e);
		
		McUserInfoView view = mcUserInfoMapper.loadUserInfo(dto.getId());
		launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(view.getUserName() + view.getPassword() , JSONObject.toJSONString(view));
		
		return null;
	}
}





























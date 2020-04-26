package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.RpcResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IAcApiDomainMapper;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.dao.IAcApiProjectMapper;
import com.matrix.dao.IAcIncludeDomainMapper;
import com.matrix.dao.IAcRequestInfoMapper;
import com.matrix.dao.IAcRequestOpenApiMapper;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.dto.AcRequestInfoDto;
import com.matrix.pojo.entity.AcApiDomain;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.entity.AcRequestOpenApi;
import com.matrix.pojo.view.AcApiInfoView;
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.AcIncludeDomainView;
import com.matrix.pojo.view.AcRequestInfoView;
import com.matrix.pojo.view.AcRequestOpenApiView;
import com.matrix.pojo.view.ApiTreeView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IApiCenterService;
import com.matrix.util.DateUtil;
import com.matrix.util.UuidUtil;

@Service("apiCenterService")
public class ApiCenterServiceImpl extends BaseServiceImpl<Long , AcApiInfo, AcApiInfoDto , AcApiInfoView> implements IApiCenterService {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Resource
	private IAcApiDomainMapper acApiDomainMapper;
	@Resource
	private IAcApiInfoMapper acApiInfoMapper;
	@Resource
	private IAcApiProjectMapper acApiProjectMapper;
	@Resource
	private IAcIncludeDomainMapper acIncludeDomainMapper;
	@Resource
	private IAcRequestInfoMapper acRequestInfoMapper;   
	@Resource
	private IAcRequestOpenApiMapper acRequestOpenApiMapper;
	

	/**
	 * @description: ac_api_project 列表数据信息
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月14日 上午9:38:58 
	 * @version 1.0.0
	 */
	public JSONObject ajaxApiProjectList(AcApiProject entity, HttpServletRequest request) {
		JSONObject result = new JSONObject();
		try {
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
			List<AcApiProjectView> list = acApiProjectMapper.queryPageList(entity); 
			result.put("status", "success");
			if (list != null && list.size() > 0) {
				result.put("code" , RpcResultCode.SUCCESS);
				result.put("msg", this.getInfo(100010114));  // 100010114=分页数据返回成功!
			} else {
				result.put("code" , RpcResultCode.RESULT_NULL);
				result.put("msg", this.getInfo(100010115));  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
			PageInfo<AcApiProjectView> pageList = new PageInfo<AcApiProjectView>(list);
			result.put("data", pageList);
			result.put("entity", entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(100010116));  // 100010116=分页数据返回失败，服务器异常!
			return result;
		}
	}

	public JSONObject ajaxBtnApiProjectAdd(AcApiProject e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(e.getTarget())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010060));  // 项目名称不得为空
			return result;
		}
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setCreateTime(new Date());
		e.setCreateUserId(u.getId());
		e.setCreateUserName(u.getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acApiProjectMapper.insertSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(600010061));  // 600010061=数据添加成功!
			launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");   
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010062));  // 600010062=服务器异常，数据添加失败!
		}
		return result;
	}

	/**
	 * @description: 更新
	 *
	 * @author Yangcl
	 * @date 2017年11月14日 下午4:07:46 
	 * @version 1.0.0
	 */
	public JSONObject ajaxBtnApiProjectEdit(AcApiProject e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(e.getTarget())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010060));  // 项目名称不得为空
			return result;
		}
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acApiProjectMapper.updateSelective(e); 
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(600010063));  // 600010063=数据修改成功!
			launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");   
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010064));  // 600010064=服务器异常，数据修改失败! 
		}
		return result;
	}
	
	/**
	 * @description: 删除
	 *
	 * @author Yangcl
	 * @date 2019年12月27日 下午3:17:41 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnApiProjectDelete(AcApiProject e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010088));  // 600010088=数据删除失败
			return result;
		}
		
		McUserInfoView user = (McUserInfoView) session.getAttribute("userInfo");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(user.getId());
		e.setUpdateUserName(user.getUserName());
		
		try {
			int flag = acApiProjectMapper.deleteById(e.getId());
			if(flag == 1) {
				result.put("status", "success");
				result.put("msg", this.getInfo(600010089));  // 600010089=数据删除成功!
				launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");   
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010090));  // 600010090=服务器异常，数据删除失败! 
			}
			return result;
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(600010090));  // 600010090=服务器异常，数据删除失败! 
		}
		return result;
	}

	
	
	/**
	 * @description: 跨域白名单列表数据请求
	 *
	 * @param entity
	 * @param request
	 * @author Yangcl
	 * @date 2017年11月15日 上午11:19:57 
	 * @version 1.0.0
	 */
	public JSONObject ajaxIncludeDomainPageList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		try {
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
			List<AcIncludeDomainView> list = acIncludeDomainMapper.queryPageList(entity); 
			result.put("status", "success");
			if (list != null && list.size() > 0) {
				result.put("code" , RpcResultCode.SUCCESS);
				result.put("msg", this.getInfo(100010114));  // 100010114=分页数据返回成功!
			} else {
				result.put("code" , RpcResultCode.RESULT_NULL);
				result.put("msg", this.getInfo(100010115));  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据! 
			}
			PageInfo<AcIncludeDomainView> pageList = new PageInfo<AcIncludeDomainView>(list);
			result.put("data", pageList);
			result.put("entity", entity);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(100010116));  // 100010116=分页数据返回失败，服务器异常!
			return result;
		}
	}
	
	/**
	 * @description: 全量跨域白名单列表数据，不分页
	 *
	 * @author Yangcl
	 * @date 2017年11月27日 下午11:22:33 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxIncludeDomainList(AcIncludeDomain entity, HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		String value = launch.loadDictCache(DCacheEnum.ApiDomain , "ApiDomainInit").get("all");  
		if (StringUtils.isNoneBlank(value)) {
			return JSONObject.parseObject(value);
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		return result;
	}

	/**
	 * @description: 添加跨域白名单
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月17日 下午11:11:25 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnApiDomainAdd(AcIncludeDomain e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(e.getDomain())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010066));  // 域名不得为空!
			return result;
		}
		if(StringUtils.isBlank(e.getCompanyName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010067));  // 所属公司不得为空!
			return result;
		}
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setCreateTime(new Date());
		e.setCreateUserId(u.getId());
		e.setCreateUserName(u.getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acIncludeDomainMapper.insertSelective(e);
		if(flag == 1) {
			result.put("status", "success");
			result.put("msg", this.getInfo(600010061));  // 600010061=数据添加成功!
			List<AcIncludeDomainView> list = acIncludeDomainMapper.queryPageList(null); 
			if(list != null && list.size() > 0) {
				JSONObject cache = new JSONObject();
				cache.put("status", "success");
				cache.put("data", list);
				launch.loadDictCache(DCacheEnum.ApiDomain , null).set("all" , cache.toJSONString());  
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010065));  // 600010065=服务器异常，数据缓存修改失败!
			}
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010062));  // 600010062=服务器异常，数据添加失败!
		}
		return result;
	}

	/**
	 * @description: 编辑跨域白名单
	 *
	 * @author Yangcl
	 * @date 2017年11月18日 下午9:56:10 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnApiDomainEdit(AcIncludeDomain e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(e.getDomain())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010066));  // 域名不得为空!
			return result;
		}
		if(StringUtils.isBlank(e.getCompanyName())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010067));  // 所属公司不得为空!
			return result;
		}
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		try {
			int flag = acIncludeDomainMapper.updateSelective(e);
			if(flag == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(600010063));  // 600010063=数据修改成功!
				launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
				launch.loadDictCache(DCacheEnum.ApiInfo , null).batchDel("");
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010023));  // 600010023=数据库异常，数据修改失败! 
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(600010064));  // 600010064=服务器异常，数据修改失败!
		}
		return result;
	}
	
	/**
	 * @description: 删除一条跨域白名单记录
	 *
	 * @author Yangcl
	 * @date 2020年1月7日 上午10:20:16 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxBtnApiDomainDelete(AcIncludeDomain e, HttpSession session) {
		JSONObject result = new JSONObject();
		if(e.getId() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010091));  // 600010091=数据删除失败，主键为空
			return result;
		}
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		e.setDeleteFlag(0);
		
		try {
			int flag = acIncludeDomainMapper.updateSelective(e);
			if(flag == 1){
				result.put("status", "success");
				result.put("msg", this.getInfo(600010089));  // 600010089=数据删除成功!
				launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
				launch.loadDictCache(DCacheEnum.ApiInfo , null).batchDel("");
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010088));  // 600010088=数据删除失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(600010090));  // 600010090=服务器异常，数据删除失败!
		}
		return result;
	}

	/**
	 * @description: 获取api树结构列表信息
	 *
	 * @param entity
	 * @param session															
	 * @author Yangcl
	 * @date 2017年11月20日 下午3:40:07 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxApiInfoList(AcApiInfo e, HttpSession session) {
		JSONObject result = new JSONObject();
		String project = launch.loadDictCache(DCacheEnum.ApiProject , "ApiProjectInit").get("all");
		if(StringUtils.isBlank(project)) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010068));  // 600010068=API树形结构加载失败!api所属项目未能正常初始化，请重试.
			return result;
		}
		JSONObject pobj = JSONObject.parseObject(project);
		if(!pobj.getString("status").equals("success")) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010069));  // 600010069=API树形结构加载失败!api所属项目缓存异常.
			return result;
		}
		
		JSONArray arr = pobj.getJSONArray("data");
		if(arr.size() == 0) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010070));  // 600010070=API树形结构加载失败!api所属项目未定义.
			return result;
		}
		List<ApiTreeView> tlist = new ArrayList<ApiTreeView>();
		ApiTreeView root = new ApiTreeView();
		root.setId(0L);
		root.setName("root"); 
		root.setSeqnum(1);
		root.setParentId(-1L); 
		tlist.add(root);
		for(int i = 0 ; i < arr.size() ; i ++) {
			JSONObject p = arr.getJSONObject(i);
			ApiTreeView a = new ApiTreeView();
			a.setId(p.getLong("id"));
			a.setName(p.getString("target"));
			a.setAtype(p.getString("atype")); 
			a.setSeqnum(i+1);
			a.setParentId(0L);
			a.setServiceUrl(p.getString("serviceUrl"));
			tlist.add(a);
		}
		List<ApiTreeView> apiInfoList = acApiInfoMapper.findApiInfoList(e);
		if(apiInfoList != null && apiInfoList.size() != 0) {
			tlist.addAll(apiInfoList);
		}
		
		result.put("status", "success");
		result.put("list", tlist);
		return result;
	}

	/**
	 * @description: 添加api信息
	 *
	 * @param dto
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月28日 下午3:19:55 
	 * @version 1.0.0
	 */
	public JSONObject ajaxApiInfoAdd(AcApiInfoDto dto, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getName() , dto.getTarget() , dto.getProcessor() , dto.getModule() , dto.getRemark())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010071));  // 600010071=API关键信息不得为空!请全部填写.
			return result;
		}
		if(dto.getDomain() == 1 && StringUtils.isBlank(dto.getDomainList())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010072));  // 600010072=请勾选API可用跨域列表
			return result;
		}
		
		if(!StringUtils.startsWithAny(dto.getProcessor() , dto.getAtype() , "common")) { 
			result.put("status", "error");
			result.put("msg", this.getInfo(600010074 , dto.getAtype()));  // 600010074=【业务处理实现】路径输入错误!应该以{0}起始
			return result;
		}
		String isRecord = launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(dto.getTarget());
		if(StringUtils.isNotBlank(isRecord)) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010075 , dto.getTarget() ));  // 600010075=系统接口名称：{0} 已经在数据库中存在,请修改.
			return result;
		}
		
		AcApiInfo e = new AcApiInfo();
		e.setName(dto.getName());
		e.setTarget(dto.getTarget());
		e.setDtoInfo(dto.getDtoInfo());
		e.setAtype(dto.getAtype());
		e.setModule(dto.getModule());
		e.setProcessor(dto.getProcessor());
		e.setDomain(dto.getDomain());
		e.setParentId(dto.getParentId());
		e.setSeqnum(dto.getSeqnum());
		e.setDiscard(1);
		e.setLogin(dto.getLogin()); 
		e.setRemark(dto.getRemark());
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setCreateTime(new Date());
		e.setCreateUserId(u.getId());
		e.setCreateUserName(u.getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acApiInfoMapper.insertSelective(e);
		if(flag == 1) {
			try {
				if(dto.getDomain() == 1) {							 
					String [] arr = dto.getDomainList().split(",");
					for(int i = 0 ; i < arr.length ; i ++) {
						AcApiDomain ad = new AcApiDomain();
						ad.setAcApiInfoId(e.getId());
						ad.setAcIncludeDomainId(Long.valueOf(arr[i]));
						
						ad.setCreateTime(new Date());
						ad.setCreateUserId(u.getId());
						ad.setCreateUserName(u.getUserName());
						ad.setUpdateTime(new Date());
						ad.setUpdateUserId(u.getId());
						ad.setUpdateUserName(u.getUserName());
						acApiDomainMapper.insertSelective(ad);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result.put("status", "error");
				result.put("msg", this.getInfo(600010063));  // 600010073=API信息与跨域域名关联异常,数据库更新失败!请及时查看服务器log日志
				return result;
			}
			
			result.put("status", "success");
			result.put("msg", this.getInfo(600010061));  // 600010061=数据添加成功!
			return result;  // 标识成功并返回全部缓存信息
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010062));  // 600010062=服务器异常，数据添加失败!
		}
		return result;
	}

	/**
	 * @description: 依据target 查找一个api信息
	 *
	 * @param dto
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月29日 下午4:26:33 
	 * @version 1.0.0
	 */
	public JSONObject ajaxApiInfoFind(AcApiInfoDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(dto.getTarget())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010076));  // 600010076=系统接口标识"target"参数不得为空!
			return result;
		}
		String record = launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(dto.getTarget());
		if(StringUtils.isBlank(record)) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010077 , dto.getTarget()));  // 600010077=目标接口: {0} 不存在!
			return result;
		}
		JSONObject cache =JSONObject.parseObject(record);
		cache.put("status", "success");
		cache.put("msg", this.getInfo(600010006));  // 600010006=数据查询成功! 
		return cache;  
	}

	/**
	 * @description: 修改api信息 
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2017年11月30日 下午3:26:55 
	 * @version 1.0.0
	 */
	public JSONObject ajaxApiInfoEdit(AcApiInfoDto dto, HttpSession session) {
		JSONObject result = new JSONObject();
		if(!StringUtils.startsWithAny(dto.getProcessor() , dto.getAtype() , "common") ) { 
			result.put("status", "error");
			result.put("msg", this.getInfo(600010074 , dto.getAtype()));  // 600010074=【业务处理实现】路径输入错误!应该以{0}起始
			return result;
		}
		if(dto.getDomain() == 1 && StringUtils.isBlank(dto.getDomainList())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010072));  // 600010072=请勾选API可用跨域列表
			return result;
		}
		if(StringUtils.isBlank(dto.getDtoInfo())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010085));  // 600010085=请求数据Json结构体不得为空!
			return result;
		}
		try {
			JSONObject.parse(dto.getDtoInfo());
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010086));  // 600010086=非法的请求数据Json结构体!请输入一个Json字符串
			return result;
		}
		AcApiInfo api = acApiInfoMapper.find(dto.getId());
		if(api == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010078 , dto.getTarget()));  // 600010078=目标接口: {0} 不存在!数据库无此记录,修改失败!
			return result;
		}
		if(!api.getTarget().equals(dto.getTarget())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010079 , dto.getTarget()));  // 600010079=目标接口: {0} 与数据库记录不符, 请勿修改【系统接口名称】
			return result;
		}
		
		AcApiInfo e = new AcApiInfo();
		e.setId(dto.getId());  
		e.setDtoInfo(dto.getDtoInfo());
		e.setProcessor(dto.getProcessor());
		e.setModule(dto.getModule());
		e.setDomain(dto.getDomain());
		e.setSeqnum(dto.getSeqnum());
//		e.setDiscard(dto.getDiscard());      // 系统接口熔断标识进行单独更新
		e.setLogin(dto.getLogin());
		e.setRemark(dto.getRemark());
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acApiInfoMapper.updateSelective(e);
		if(flag == 1) {
			try {
				if(dto.getDomain() == 1) {		
					// 删除旧关联关系
					acApiDomainMapper.deleteByApiInfoId(e.getId());
					String [] arr = dto.getDomainList().split(",");
					for(int i = 0 ; i < arr.length ; i ++) {
						AcApiDomain ad = new AcApiDomain();
						ad.setAcApiInfoId(e.getId());
						ad.setAcIncludeDomainId(Long.valueOf(arr[i]));
						
						ad.setCreateTime(new Date());
						ad.setCreateUserId(u.getId());
						ad.setCreateUserName(u.getUserName());
						ad.setUpdateTime(new Date());
						ad.setUpdateUserId(u.getId());
						ad.setUpdateUserName(u.getUserName());
						acApiDomainMapper.insertSelective(ad);
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				result.put("status", "error");
				result.put("msg", this.getInfo(600010063));  // 600010073=API信息与跨域域名关联异常,数据库更新失败!请及时查看服务器log日志
				return result;
			}
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010064));  // 600010064=服务器异常，数据修改失败! 
		}
		
		launch.loadDictCache(DCacheEnum.ApiInfo , null).del(api.getTarget()); 
		JSONObject cache = JSONObject.parseObject( launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(api.getTarget()) ); 
		cache.put("status", "success");
		cache.put("msg", this.getInfo(600010080));  // 600010080=API接口信息修改成功!
		return cache;
	}
	
	/**
	 * @description: 删除一个API
	 *
	 * @author Yangcl
	 * @date 2020年1月10日 下午4:44:37 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxApiInfoRemove(AcApiInfoDto dto, HttpSession session) {
		JSONObject result = new JSONObject();
		try {
			AcApiInfo api = acApiInfoMapper.find(dto.getId());
			if(api == null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010078 , dto.getTarget()));  // 600010078=目标接口: {0} 不存在!数据库无此记录,修改失败!
				return result;
			}
			
			AcApiInfo e = new AcApiInfo();
			e.setId(dto.getId());  
			e.setDeleteFlag(0);
			McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
			e.setUpdateTime(new Date());
			e.setUpdateUserId(u.getId());
			e.setUpdateUserName(u.getUserName());
			int flag = acApiInfoMapper.updateSelective(e);
			if(flag == 1) {
				result.put("status", "success");
				result.put("msg", this.getInfo(600010089));  // 600010089=数据删除成功!
				launch.loadDictCache(DCacheEnum.ApiInfo , null).del(api.getTarget()); 
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010088));  // 600010088=数据删除失败!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(600010090));  // 600010090=服务器异常，数据删除失败! 
			return result;
		}
		return result;
	}
	
	/**
	 * @description: 系统接口熔断：恢复启用|立刻熔断
	 *
	 * @author Yangcl
	 * @date 2020年1月13日 下午2:48:18 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxApiInfoDiscard(AcApiInfo entity, HttpSession session) {
		JSONObject result = new JSONObject();
		try {
			AcApiInfo api = acApiInfoMapper.find(entity.getId());
			if(api == null) {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010078 , entity.getTarget()));  // 600010078=目标接口: {0} 不存在!数据库无此记录,修改失败!
				return result;
			}
			
			AcApiInfo e = new AcApiInfo();
			e.setId(entity.getId());  
			e.setDiscard(entity.getDiscard());
			McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
			e.setUpdateTime(new Date());
			e.setUpdateUserId(u.getId());
			e.setUpdateUserName(u.getUserName());
			int flag = acApiInfoMapper.updateSelective(e);
			if(flag == 1) {
				result.put("status", "success");
				result.put("msg", this.getInfo(600010063));  // 600010063=数据修改成功!
				launch.loadDictCache(DCacheEnum.ApiInfo , null).del(api.getTarget()); 
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(100010123));  // 100010123=数据修改失败，数据库连接异常!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.put("status", "error");
			result.put("msg", this.getInfo(600010064));  // 600010064=服务器异常，数据修改失败! 
			return result;
		}
		return result;
	}

	/**
	 * @description: 请求者信息维护页面
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月1日 上午10:42:52 
	 * @version 1.0.0
	 */
	public String requestInfoList() {
		return "jsp/api/request/api-request-info-list";
	}

	/**
	 * @description: 接口请求者列表分页数据
	 *
	 * @param entity
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月1日 上午11:32:43 
	 * @version 1.0.0
	 */
	public JSONObject ajaxRequestInfoList(AcRequestInfo entity, HttpServletRequest request, HttpSession session) {
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
		List<AcRequestInfoView> list = acRequestInfoMapper.queryPageList(entity); 
		if (list != null && list.size() > 0) {
			result.put("status", "success");
		} else {
			result.put("status", "error");
			result.put("msg", this.getInfo(100090002));  // 没有查询到可以显示的数据 
		}
		PageInfo<AcRequestInfoView> pageList = new PageInfo<AcRequestInfoView>(list);
		result.put("data", pageList);
		result.put("entity", entity);
		return result;
	}

	/**
	 * @description: ac_request_info添加数据
	 *
	 * @param entity
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月1日 下午1:42:20 
	 * @version 1.0.0
	 */
	public JSONObject ajaxRequestInfoAdd(AcRequestInfo e, HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(e.getOrganization() , e.getAtype())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010081));  // 600010081=接口请求者关键信息不得为空! 
			return result;
		}
		DateUtil dateUtil = new DateUtil();
		e.setKey(dateUtil.getDateLongHex("yyyyMMdd").toUpperCase() + dateUtil.getDateLongHex("HHmmss").toUpperCase());  
		e.setValue(UuidUtil.uid().toUpperCase());  
		e.setFlag(1); 
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		e.setCreateTime(new Date());
		e.setCreateUserId(u.getId());
		e.setCreateUserName(u.getUserName());
		e.setUpdateTime(new Date());
		e.setUpdateUserId(u.getId());
		e.setUpdateUserName(u.getUserName());
		
		int flag = acRequestInfoMapper.insertSelective(e);
		if(flag == 1) {
			// 开始初始化API缓存
			JSONObject cache = JSONObject.parseObject(JSONObject.toJSONString(e)); 
			cache.put("list", new ArrayList<String>());  // api target list is null when add a record to database, but keep the cache structure.
			launch.loadDictCache(DCacheEnum.ApiRequester , null).set(e.getKey() , cache.toJSONString()); 
			
			result.put("status", "success");
			result.put("msg", this.getInfo(600010061));  // 600010061=数据添加成功!
			return result; 
		}else {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010062));  // 600010062=服务器异常，数据添加失败!
		}
		return result;
	}

	/**
	 * @description:编辑信息(organization & atype)|启用/禁用(flag)|为第三方调用者分配系统开放接口(open-api)
	 *
	 * @param dto
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月1日 下午2:21:07 
	 * @version 1.0.0
	 */
	public JSONObject ajaxRequestInfoEdit(AcRequestInfoDto d, HttpServletRequest request, HttpSession session) {
		JSONObject result = new JSONObject();
		if(d.getId() == null || d.getIsallot() == null) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010083));  // 600010083=主键丢失
			return result;
		}
		if(d.getIsallot() == 1 && StringUtils.isBlank(d.getTargets())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010082));  // 600010082=接口请求者关联API信息不得为空!
			return result;
		}
		AcRequestInfo e = acRequestInfoMapper.find(d.getId());
		if(d.getIsallot() ==1 && e.getAtype().equals("private")) {
			result.put("status", "error");
			result.put("msg", this.getInfo(600010084));  // 600010084=内部接口请求者不可分配开放接口数据(open-api)!
			return result;
		}
		
		McUserInfoView u = (McUserInfoView) session.getAttribute("userInfo");
		d.setUpdateTime(new Date());
		d.setUpdateUserId(u.getId()); 
		d.setUpdateUserName(u.getUserName());  
		
		if(d.getIsallot() ==1) { 
			String [] arr = d.getTargets().split(",");
			for(int i = 0 ; i < arr.length ; i ++) {
				AcRequestOpenApi roa = new AcRequestOpenApi();
				roa.setAcRequestInfoId(d.getId());
				roa.setAcApiInfoId(Long.valueOf(arr[i]));
				
				roa.setCreateTime(new Date());
				roa.setCreateUserId(u.getId());
				roa.setCreateUserName(u.getUserName());
				roa.setUpdateTime(new Date());
				roa.setUpdateUserId(u.getId());
				roa.setUpdateUserName(u.getUserName());
				acRequestOpenApiMapper.insertSelective(roa); 
			}
		}else {  // 编辑信息(organization & atype)|启用/禁用(flag)
			AcRequestInfo e_ = new AcRequestInfo(); 
			e_.setId(d.getId()); 
			e_.setOrganization(d.getOrganization());
			e_.setKey(d.getKey());
			e_.setValue(d.getValue());
			e_.setAtype(d.getAtype());
			e_.setFlag(d.getFlag());
			e_.setUpdateTime(new Date());
			e_.setUpdateUserId(u.getId()); 
			e_.setUpdateUserName(u.getUserName());  
			int flag = acRequestInfoMapper.updateSelective(e_); 
			if(flag != 1) {
				result.put("status", "error");
				result.put("msg", this.getInfo(600010064));  // 600010064=服务器异常，数据修改失败! 
				return result;
			}
		}
		
		e = acRequestInfoMapper.find(d.getId());
		// 开始初始化API缓存
		JSONObject cache = JSONObject.parseObject(JSONObject.toJSONString(e)); 
		if(e.getAtype().equals("public")) {
			List<AcRequestOpenApiView> list = acRequestOpenApiMapper.findListById(d.getId());
			if(list == null || list.size() == 0) {
				cache.put("list", new ArrayList<String>()); 
			}else {
				List<String> list_ = new ArrayList<String>();
				for(AcRequestOpenApiView v : list) {
					list_.add(v.getTarget());
				}
				cache.put("list", list_); 
			}
		}else {
			cache.put("list", new ArrayList<String>());   
		}
		
		launch.loadDictCache(DCacheEnum.ApiRequester , null).set(e.getKey() , cache.toJSONString()); 
		result.put("status", "success");
		result.put("msg", this.getInfo(600010063));  // 600010063=数据修改成功!
		return result;
	}

	
	/**
	 * @description: 前往接口测试页面
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月11日 上午11:46:32 
	 * @version 1.0.0.1
	 */
	public String pageApicenterApiTest() {
		return "jsp/api/test/api-test-page"; 
	}

	/**
	 * @description: 根据接口target，返回查询消息体
	 *
	 * @param target
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月11日 下午4:57:09 
	 * @version 1.0.0
	 */
	public JSONObject ajaxFindRequestDto(String target) {
		JSONObject result = new JSONObject();
		String apiInfoStr = launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(target);  
		System.out.println("apiInfoStr" + apiInfoStr);
		if(StringUtils.isBlank(apiInfoStr)){
			result.put("status", "error");
			result.put("code", "10014"); 
			result.put("msg", this.getInfo(600010014));  // 600010014=系统未检测到您所访问的接口
			return result;
		} 
		JSONObject apiInfo = JSONObject.parseObject(apiInfoStr);
		String class_ = apiInfo.getString("processor");
		if(StringUtils.isBlank(class_)) {
			result.put("status", "error");
			result.put("code", "10014"); 
			result.put("msg", this.getInfo(600010015));  // 600010015=系统未检测到对应接口处理类!请联系开发人员!
			return result;
		}
		
		try {
			Class<?> clazz = Class.forName("com.matrix.processor." + class_);   
			System.out.println("class = " + clazz.toString()); 
			if (clazz != null && clazz.getDeclaredMethods() != null){
				MatrixRequest dto = clazz.getAnnotation(MatrixRequest.class);
				if(dto != null) { 
					result.put("status", "success");
					result.put("dto", dto.clazz().newInstance());  
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "error");
			result.put("code", "10010");
			result.put("msg", this.getInfo(600010011));  // 600010011=系统错误, 请联系开发人员!
			return result; 
		}
		
		return result;
	}

	/**
	 * @description: 根据请求者的key，找到对应的value
	 *
	 * @param key
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月25日 下午10:11:23 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxFindRequestValue(String key) {
		JSONObject result = new JSONObject();
		String requestInfo = launch.loadDictCache(DCacheEnum.ApiRequester , "InitApiRequester").get(key);  // ac_request_info表的缓存
		if(StringUtils.isBlank(requestInfo)) {
			result.put("status", "error");
			result.put("code", "10012"); 
			result.put("msg", this.getInfo(600010012));  // 非法的请求! 您请求的公钥未包含在我们的系统中.
			return result;
		}
		JSONObject requester = JSONObject.parseObject(requestInfo);
		if(StringUtils.isBlank(requester.getString("value"))) {
			result.put("status", "error");
			result.put("code", "10002");
			result.put("msg", this.getInfo(600010002));  // 系统秘钥数据为空，请联系开发人员，为您带来不便请谅解!
			return result;
		}
		
		result.put("status", "success");
		result.put("data", requester.getString("value"));   
		return result;
	}
	
}











































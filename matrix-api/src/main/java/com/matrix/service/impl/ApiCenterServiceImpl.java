package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseServiceImpl;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
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
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.dto.ValidationTest;
import com.matrix.pojo.entity.AcApiDomain;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.entity.AcRequestOpenApi;
import com.matrix.pojo.request.AddAcIncludeDomainRequest;
import com.matrix.pojo.request.AddApiInfoRequest;
import com.matrix.pojo.request.AddApiProjectListRequest;
import com.matrix.pojo.request.AddRequestInfoRequest;
import com.matrix.pojo.request.DeleteAcIncludeDomainRequest;
import com.matrix.pojo.request.DeleteApiInfoRequest;
import com.matrix.pojo.request.DeleteApiProjectListRequest;
import com.matrix.pojo.request.FindAcIncludeDomainListRequest;
import com.matrix.pojo.request.FindApiInfoListRequest;
import com.matrix.pojo.request.FindApiInfoRequest;
import com.matrix.pojo.request.FindApiProjectListRequest;
import com.matrix.pojo.request.FindRequestInfoListRequest;
import com.matrix.pojo.request.UpdateAcIncludeDomainRequest;
import com.matrix.pojo.request.UpdateApiInfoDiscardRequest;
import com.matrix.pojo.request.UpdateApiInfoRequest;
import com.matrix.pojo.request.UpdateApiProjectListRequest;
import com.matrix.pojo.request.UpdateRequestInfoRequest;
import com.matrix.pojo.view.AcApiInfoView;
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.AcIncludeDomainView;
import com.matrix.pojo.view.AcRequestInfoView;
import com.matrix.pojo.view.ApiTreeView;
import com.matrix.service.IApiCenterService;

@Validated
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
	 * @author Yangcl
	 * @date 2017年11月14日 上午9:38:58 
	 * @version 1.0.0
	 */
	public Result<PageInfo<AcApiProjectView>> ajaxApiProjectList(FindApiProjectListRequest param, HttpServletRequest request) {
		AcApiProject entity = param.buildAjaxApiProjectList();
		int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = entity.getStartIndex();
				pageSize = entity.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<AcApiProjectView> list = acApiProjectMapper.queryPageList(entity); 
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<AcApiProjectView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}

	@Transactional
	public Result<?> ajaxBtnApiProjectAdd(AddApiProjectListRequest param, HttpSession session) {
		try {
			AcApiProject e = param.buildAjaxBtnApiProjectAdd();
			int flag = acApiProjectMapper.insertSelective(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");   
				return Result.SUCCESS(this.getInfo(100010102));  		// 100010102=数据添加成功!
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010103));		// 100010103=数据添加失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);	// 100010103=数据添加失败，服务器异常!
	}

	/**
	 * @description: 更新
	 *
	 * @author Yangcl
	 * @date 2017年11月14日 下午4:07:46 
	 * @version 1.0.0
	 */
	@Transactional
	public Result<?> ajaxBtnApiProjectEdit(UpdateApiProjectListRequest param, HttpSession session) {
		AcApiProject e = param.buildAjaxBtnApiProjectEdit();
		try {
			int flag = acApiProjectMapper.updateSelective(e); 
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");
				return Result.SUCCESS(this.getInfo(100010104));  // 100010104=数据更新成功!
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010105));		// 100010105=数据更新失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);  // 100010105=数据更新失败，服务器异常!
	}
	
	/**
	 * @description: 删除
	 *
	 * @author Yangcl
	 * @date 2019年12月27日 下午3:17:41 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnApiProjectDelete(DeleteApiProjectListRequest param, HttpSession session) {
		try {
			int flag = acApiProjectMapper.deleteById(param.getId());
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");   
				return Result.SUCCESS(this.getInfo(100010106));   	// 100010106=数据删除成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010107));		// 100010107=数据删除失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010107), ResultCode.ERROR_DELETE); 		// 100010107=数据删除失败，服务器异常!
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
	public Result<PageInfo<AcIncludeDomainView>> ajaxIncludeDomainPageList(FindAcIncludeDomainListRequest param, HttpServletRequest request, HttpSession session) {
		AcIncludeDomain entity = param.buildAjaxIncludeDomainPageList();
		int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = entity.getStartIndex();
				pageSize = entity.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<AcIncludeDomainView> list = acIncludeDomainMapper.queryPageList(entity);
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<AcIncludeDomainView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}
	
	/**
	 * @description: 全量跨域白名单列表数据，不分页
	 *
	 * @author Yangcl
	 * @date 2017年11月27日 下午11:22:33 
	 * @version 1.0.0.1
	 */
	public Result<List<AcIncludeDomainView>> ajaxIncludeDomainList(HttpServletRequest request, HttpSession session) {
		String value = launch.loadDictCache(DCacheEnum.ApiDomain , "ApiDomainInit").get("all");  
		if (StringUtils.isNotBlank(value)) {
			String jsonArrStr = JSONObject.parseObject(value).getJSONArray("data").toJSONString();
			List<AcIncludeDomainView> list = JSONArray.parseArray(jsonArrStr, AcIncludeDomainView.class);
			return Result.SUCCESS(list);
		}
		return Result.SUCCESS(this.getInfo(100090002), ResultCode.RESULT_NULL);		 // 没有查询到可以显示的数据 
	}

	/**
	 * @description: 添加跨域白名单
	 *
	 * @author Yangcl
	 * @date 2017年11月17日 下午11:11:25 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> ajaxBtnApiDomainAdd(AddAcIncludeDomainRequest param, HttpSession session) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcIncludeDomain e = param.buildAjaxBtnAcIncludeDomainAdd();
			int flag = acIncludeDomainMapper.insertSelective(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
				return Result.SUCCESS(this.getInfo(100010102));  		// 100010102=数据添加成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010103));		// 100010103=数据添加失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010103), ResultCode.ERROR_INSERT);	// 100010103=数据添加失败，服务器异常!
	}

	/**
	 * @description: 编辑跨域白名单
	 *
	 * @author Yangcl
	 * @date 2017年11月18日 下午9:56:10 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnApiDomainEdit(UpdateAcIncludeDomainRequest param, HttpSession session) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcIncludeDomain e = param.buildAjaxBtnAcIncludeDomainEdit();
			int flag = acIncludeDomainMapper.updateSelective(e);
			if(flag == 1){
				launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
				launch.loadDictCache(DCacheEnum.ApiInfo , null).batchDeleteByPrefix("");
				return Result.SUCCESS(this.getInfo(100010104));  // 100010104=数据更新成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010105));		// 100010105=数据更新失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);  // 100010105=数据更新失败，服务器异常!
	}
	
	/**
	 * @description: 删除一条跨域白名单记录
	 *
	 * @author Yangcl
	 * @date 2020年1月7日 上午10:20:16 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnApiDomainDelete(DeleteAcIncludeDomainRequest param, HttpSession session) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcIncludeDomain e = param.buildAjaxBtnAcIncludeDomainDelete();
			int flag = acIncludeDomainMapper.updateSelective(e);
			if(flag == 1){
				launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
				launch.loadDictCache(DCacheEnum.ApiInfo , null).batchDeleteByPrefix("");
				return Result.SUCCESS(this.getInfo(100010106));   	// 100010106=数据删除成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010107));		// 100010107=数据删除失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010107), ResultCode.ERROR_DELETE); 		// 100010107=数据删除失败，服务器异常!
	}

	/**
	 * @description: 获取api树结构列表信息
	 *
	 * @author Yangcl
	 * @date 2017年11月20日 下午3:40:07 
	 * @version 1.0.0.1
	 */
	public Result<List<ApiTreeView>> ajaxApiInfoList(FindApiInfoListRequest param, HttpSession session) {
		Result<List<ApiTreeView>> validate = param.validate(launch);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		
		AcApiInfo e = param.buildAjaxApiInfoList();
		JSONArray arr = param.getArr();
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
		return Result.SUCCESS(this.getInfo(100020100), tlist);
	}

	/**
	 * @description: 添加api信息
	 *
	 * @author Yangcl
	 * @date 2017年11月28日 下午3:19:55 
	 * @version 1.0.0
	 */
	@Transactional
	public Result<AcApiInfo> ajaxApiInfoAdd(AddApiInfoRequest param, HttpSession session) {
		Result<AcApiInfo> validate = param.validate(launch);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcApiInfo e = param.buildAjaxApiInfoAdd();
			int flag = acApiInfoMapper.insertSelective(e);
			if(flag == 1) {
				if(param.getDomain() == 1) {							 
					String [] arr = param.getDomainList().split(",");
					for(int i = 0 ; i < arr.length ; i ++) {
						AcApiDomain ad = new AcApiDomain();
						ad.setAcApiInfoId(e.getId());
						ad.setAcIncludeDomainId(Long.valueOf(arr[i]));
						ad.buildAddCommon(param.getUserCache());
						acApiDomainMapper.insertSelective(ad);
					}
				}
				return Result.SUCCESS(this.getInfo(100010102), e);  // 100010102=数据添加成功!
			}
			return Result.ERROR(this.getInfo(100010103), ResultCode.SERVER_EXCEPTION);	// 100010103=数据添加失败，服务器异常!
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(600010092));   // 600010092=API接口信息添加失败，数据已回滚
		}
	}

	/**
	 * @description: 依据target 查找一个api信息
	 *
	 * @author Yangcl
	 * @date 2017年11月29日 下午4:26:33 
	 * @version 1.0.0
	 */
	public Result<AcApiInfoCache> ajaxApiInfoFind(FindApiInfoRequest param) {
		Result<AcApiInfoCache> validate = param.validate(launch);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		AcApiInfoCache acApiInfo = JSON.parseObject(param.getRecord(), AcApiInfoCache.class);
		return Result.SUCCESS(this.getInfo(100020100) , acApiInfo);
	}
	
	/**
	 * @description: 修改api信息 
	 *
	 * @author Yangcl
	 * @date 2017年11月30日 下午3:26:55 
	 * @version 1.0.0
	 */
	@Transactional
	public Result<AcApiInfoCache> ajaxApiInfoEdit(UpdateApiInfoRequest param, HttpSession session) {
		Result<AcApiInfoCache> validate = param.validate(acApiInfoMapper);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcApiInfo e = param.buildAjaxApiInfoEdit();
			int flag = acApiInfoMapper.updateSelective(e);
			if(flag != 1) {
				return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);
			}
			if(param.getDomain() == 1) {	
				// 删除旧关联关系
				acApiDomainMapper.deleteByApiInfoId(e.getId());
				String [] arr = param.getDomainList().split(",");
				for(int i = 0 ; i < arr.length ; i ++) {
					AcApiDomain ad = new AcApiDomain();
					ad.setAcApiInfoId(e.getId());
					ad.setAcIncludeDomainId(Long.valueOf(arr[i]));
					ad.buildAddCommon(param.getUserCache());
					acApiDomainMapper.insertSelective(ad);
				}
			}
			launch.loadDictCache(DCacheEnum.ApiInfo , null).del(param.getTarget());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(600010093));   // 600010093=API接口信息修改失败，数据已回滚
		}
		
		String record = launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(param.getTarget());
		AcApiInfoCache acApiInfo = JSON.parseObject(record, AcApiInfoCache.class);
		return Result.SUCCESS(this.getInfo(600010080) , acApiInfo);
	}
	
	/**
	 * @description: 删除一个API
	 *
	 * @author Yangcl
	 * @date 2020年1月10日 下午4:44:37 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxApiInfoRemove(DeleteApiInfoRequest param, HttpSession session) {
		Result<AcApiInfoCache> validate = param.validate(acApiInfoMapper);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			AcApiInfo e = param.buildAjaxApiInfoRemove();
			int flag = acApiInfoMapper.updateSelective(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiInfo , null).del(param.getTarget());
				return Result.SUCCESS(this.getInfo(100010106));   	// 100010106=数据删除成功!
			}
			return Result.ERROR(this.getInfo(100010107), ResultCode.ERROR_DELETE); // 100010107=数据删除失败，服务器异常!
		} catch (Exception ex) {
			ex.printStackTrace();
			return Result.ERROR(this.getInfo(100010112), ResultCode.SERVER_EXCEPTION);  // 100010112=服务器异常! 
		}
	}
	
	/**
	 * @description: 系统接口熔断：恢复启用|立刻熔断
	 *
	 * @author Yangcl
	 * @date 2020年1月13日 下午2:48:18 
	 * @version 1.0.0.1
	 */
	@Transactional
	public Result<?> ajaxApiInfoDiscard(UpdateApiInfoDiscardRequest param, HttpSession session) {
		try {
			Result<?> validate = param.validate(acApiInfoMapper);
			if(validate.getStatus().equals("error")) {
				return validate;
			}
			AcApiInfo e = param.buildAjaxApiInfoDiscard();
			int flag = acApiInfoMapper.updateSelective(e);
			if(flag == 1) {
				launch.loadDictCache(DCacheEnum.ApiInfo , null).del(param.getApi().getTarget());
				return Result.SUCCESS(this.getInfo(100010104));		// 100010104=数据更新成功!
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010105));		// 100010105=数据更新失败，服务器异常!
		}
		return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);		// 100010105=数据更新失败，服务器异常!
	}

	/**
	 * @description: 请求者信息维护页面
	 *
	 * @author Yangcl
	 * @date 2017年12月1日 上午10:42:52 
	 * @version 1.0.0
	 */
	public String requestInfoList() {
		return "views/api/request/api-request-info-list";
	}

	/**
	 * @description: 接口请求者列表分页数据
	 *
	 * @author Yangcl
	 * @date 2017年12月1日 上午11:32:43 
	 * @version 1.0.0
	 */
	public Result<PageInfo<AcRequestInfoView>> ajaxRequestInfoList(FindRequestInfoListRequest param, HttpServletRequest request, HttpSession session) {
		AcRequestInfo entity = param.build();
		int pageNum = 1;	// 当前第几页 | 必须大于0
    	int pageSize = 10;	// 当前页所显示记录条数
		try {
			if(StringUtils.isAnyBlank(request.getParameter("pageNum") , request.getParameter("pageSize"))){
				pageNum = entity.getStartIndex();
				pageSize = entity.getPageSize();
			}else{
				pageNum = Integer.parseInt(request.getParameter("pageNum")); 
				pageSize = Integer.parseInt(request.getParameter("pageSize")); 
			}
			PageHelper.startPage(pageNum , pageSize);
			List<AcRequestInfoView> list = acRequestInfoMapper.queryPageList(entity);
			if (list != null && list.size() > 0) {
				return Result.SUCCESS(this.getInfo(100010114), new PageInfo<AcRequestInfoView>(list));  // 100010114=分页数据返回成功!
			}else {
				return Result.SUCCESS(this.getInfo(100010115), ResultCode.RESULT_NULL);  // 100010115=分页数据返回成功, 但没有查询到可以显示的数据!
			}
		} catch (Exception e) {
			e.printStackTrace();
			return Result.ERROR(this.getInfo(100010116), ResultCode.SERVER_EXCEPTION);   // 100010116=分页数据返回失败，服务器异常!
		}
	}

	/**
	 * @description: ac_request_info添加数据
	 *
	 * @author Yangcl
	 * @date 2017年12月1日 下午1:42:20 
	 * @version 1.0.0
	 */
	public Result<?> ajaxRequestInfoAdd(AddRequestInfoRequest param, HttpServletRequest request, HttpSession session) {
		Result<?> validate = param.validate();
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		AcRequestInfo e = param.buildAjaxRequestInfoAdd();
		int flag = acRequestInfoMapper.insertSelective(e);
		if(flag == 1) {
			// 开始初始化API缓存
			launch.loadDictCache(DCacheEnum.ApiRequester , "ApiRequesterInit").get(e.getKey());
			return Result.SUCCESS(this.getInfo(100010102), e);  // 100010102=数据添加成功!
		}
		return Result.ERROR(this.getInfo(100010103), ResultCode.SERVER_EXCEPTION);	// 100010103=数据添加失败，服务器异常!
	}

	/**
	 * @description:编辑信息(organization & atype)|启用/禁用(flag)|为第三方调用者分配系统开放接口(open-api)
	 *
	 * @param dto.isallot 标识执行条件，0: 【编辑信息】和【启用/禁用】| 1: 为这个open-api的请求者分配可以请求的接口 TODO 此功能尚未有界面功能开发。
	 * @author Yangcl
	 * @date 2017年12月1日 下午2:21:07 
	 * @version 1.0.0
	 */
	@Transactional
	public Result<?> ajaxRequestInfoEdit(UpdateRequestInfoRequest param, HttpServletRequest request, HttpSession session) {
		Result<?> validate = param.validate(acRequestInfoMapper);
		if(validate.getStatus().equals("error")) {
			return validate;
		}
		try {
			if(param.getIsallot() ==1) { 			// TODO 为这个请求者分配他能够请求的接口。
				String [] arr = param.getTargets().split(",");
				for(int i = 0 ; i < arr.length ; i ++) {
					AcRequestOpenApi roa = new AcRequestOpenApi();
					roa.setAcRequestInfoId(param.getId());
					roa.setAcApiInfoId(Long.valueOf(arr[i]));
					roa.buildAddCommon(param.getUserCache());
					acRequestOpenApiMapper.insertSelective(roa); 
				}
			}else {  // 编辑信息(organization & atype)|启用/禁用(flag)
				AcRequestInfo e = param.buildAjaxRequestInfoEdit();
				int flag = acRequestInfoMapper.updateSelective(e); 
				if(flag != 1) {
					return Result.ERROR(this.getInfo(100010105), ResultCode.ERROR_UPDATE);
				}
				// TODO 如果从开发接口更新为内部接口，还需要acRequestOpenApiMapper软删除关联的信息
			}
			launch.loadDictCache(DCacheEnum.ApiRequester , null).del(param.getOld().getKey()); // 删除缓存，获取该缓存时会自动加载，少写冗余代码。
			return Result.SUCCESS(this.getInfo(100010104));		// 100010104=数据更新成功!
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(this.getInfo(100010105));		// 100010105=数据更新失败，服务器异常!
		}
	}

	
	/**
	 * @description: 前往接口测试页面
	 *
	 * @author Yangcl
	 * @date 2017年12月11日 上午11:46:32 
	 * @version 1.0.0.1
	 */
	public String pageApicenterApiTest() {
		return "jsp/api/test/api-test-page"; 
	}

	/**
	 * @description: 根据请求者的key，找到对应的value
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2017年12月25日 下午10:11:23 
	 * @version 1.0.0.1
	 */
	public Result<String> ajaxFindRequestValue(String key) {
		String requestInfo = launch.loadDictCache(DCacheEnum.ApiRequester , "ApiRequesterInit").get(key);  // ac_request_info表的缓存
		if(StringUtils.isBlank(requestInfo)) { // 10012 非法的请求! 您请求的公钥未包含在我们的系统中.
			return Result.ERROR(this.getInfo(600010012), 10012);
		}
		JSONObject requester = JSONObject.parseObject(requestInfo);
		if(StringUtils.isBlank(requester.getString("value"))) { // 10002 系统秘钥数据为空，请联系开发人员，为您带来不便请谅解!
			return Result.ERROR(this.getInfo(600010002), 10002);
		}
		return Result.SUCCESS(this.getInfo(100020100), requester.getString("value"));
	}
	
	
	/**
	 * @description: 根据接口target，返回查询消息体
	 *
	 * @param target
	 * @author Yangcl
	 * @date 2017年12月11日 下午4:57:09 
	 * @version 1.0.0
	 */
	public Result<Object> ajaxFindRequestDto(String target) {
		String apiInfoStr = launch.loadDictCache(DCacheEnum.ApiInfo , "ApiInfoInit").get(target);  
		if(StringUtils.isBlank(apiInfoStr)){ 	// 600010014=系统未检测到您所访问的接口
			return Result.ERROR(this.getInfo(600010014), 10014); 
		} 
		JSONObject apiInfo = JSONObject.parseObject(apiInfoStr);
		String class_ = apiInfo.getString("processor");
		if(StringUtils.isBlank(class_)) { // 600010015=系统未检测到对应接口处理类!请联系开发人员!
			return Result.ERROR(this.getInfo(600010015), 10015);
		}
		
		try {
			Class<?> clazz = Class.forName("com.matrix.processor." + class_);   
			if (clazz != null && clazz.getDeclaredMethods() != null){
				MatrixRequest dto = clazz.getAnnotation(MatrixRequest.class);
				if(dto != null) {
					return Result.SUCCESS(dto.clazz().newInstance());
				}
				return Result.ERROR(this.getInfo(600010021, clazz.getName()), ResultCode.OPERATION_FAILED);		// 600010021=目标类{0}未发现MatrixRequest标签
			}
			return Result.ERROR(this.getInfo(100020101), ResultCode.OPERATION_FAILED);
		} catch (Exception e) {
			e.printStackTrace();// 100020112=系统错误, 请联系开发人员!
			return Result.ERROR(this.getInfo(100020112), ResultCode.SERVER_EXCEPTION);
		}
	}

	@Override
	public Result<String> ajaxValidationTest(@Valid ValidationTest dto) {
		
		return Result.SUCCESS("ajaxValidationTest");
	}
}











































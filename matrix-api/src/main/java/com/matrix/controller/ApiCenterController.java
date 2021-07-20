package com.matrix.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.matrix.base.BaseController;
import com.matrix.base.Result;
import com.matrix.pojo.cache.AcApiInfoCache;
import com.matrix.pojo.entity.AcApiInfo;
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
import com.matrix.pojo.view.AcApiProjectView;
import com.matrix.pojo.view.AcIncludeDomainView;
import com.matrix.pojo.view.AcRequestInfoView;
import com.matrix.pojo.view.ApiTreeView;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.IApiCenterService;

/**
 * @description: api模块的系统页面配置信息入口。后台页面的行为在此处统一封装。 
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月13日 下午3:42:46 
 * @version 1.0.0
 */
@Controller
@RequestMapping("apicenter")
public class ApiCenterController extends BaseController {
	private static Logger logger=Logger.getLogger(ApiCenterController.class);
	
	@Autowired
	private IApiCenterService service;  
	
	
	//////////////////////////////////////////////////////////////////////////////【api所属项目】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 跳转到api所属项目列表页面
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月13日 下午7:50:27 
	 * @version 1.0.0
	 */
	@RequestMapping("page_apicenter_api_project_list")  
	public String apiProjectList(HttpSession session){ 
		super.userBehavior(session, logger, "page_apicenter_api_project_list", "前往api所属项目列表");
		return "views/api/project/api-project-list";
	}
	
	/**
	 * @description: ac_api_project 列表数据信息
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月14日 上午9:38:58 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_api_project_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<PageInfo<AcApiProjectView>> ajaxApiProjectList(FindApiProjectListRequest param , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_project_list", "获取api所属项目列表页数据");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiProjectList(param, request);  
	}
	
	/**
	 * @description: ac_api_project表添加信息
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月14日 上午10:34:12 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_btn_api_project_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiProjectAdd(AddApiProjectListRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_add", "向ac_api_project表添加信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiProjectAdd(param, session);  
	}
	
	/**
	 * @description: ac_api_project表修改信息
	 *
	 * @author Yangcl
	 * @date 2017年11月14日 上午10:34:12 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_btn_api_project_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiProjectEdit(UpdateApiProjectListRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_edit", "向ac_api_project表修改信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiProjectEdit(param, session);  
	}
	
	/**
	 * @description: 删除一条记录
	 *
	 * @author Yangcl
	 * @date 2019年12月27日 下午2:59:18 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_project_delete", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiProjectDelete(DeleteApiProjectListRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_delete", "向ac_api_project表删除一条记录");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiProjectDelete(param, session);
	}
	
	//////////////////////////////////////////////////////////////////////////////【跨域白名单】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 前往跨域白名单列表页面
	 *
	 * @author Yangcl
	 * @date 2017年11月15日 上午11:19:17 
	 * @version 1.0.0
	 */
	@RequestMapping("page_apicenter_include_domain_list")  
	public String apiIncludeDomainList(HttpSession session){ 
		super.userBehavior(session, logger, "page_apicenter_include_domain_list", "前往跨域白名单列表页面");
		return "views/api/domain/api-include-domain-list";
	}
	/**
	 * @description: 跨域白名单列表数据请求
	 *
	 * @author Yangcl
	 * @date 2017年11月15日 上午11:19:57 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_include_domain_page_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<PageInfo<AcIncludeDomainView>> ajaxIncludeDomainPageList(FindAcIncludeDomainListRequest param , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_include_domain_page_list", "ac_include_domain 跨域白名单列表数据请求");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxIncludeDomainPageList(param, request, session);  
	}
	/**
	 * @description: 全量跨域白名单列表数据，不分页
	 *
	 * @author Yangcl
	 * @date 2017年11月27日 下午11:22:33 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_include_domain_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<List<AcIncludeDomainView>> ajaxIncludeDomainList(HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_include_domain_list", "ac_include_domain 全量跨域白名单列表数据，不分页");
		return service.ajaxIncludeDomainList(request, session);  
	}
	/**
	 * @description: 添加跨域白名单
	 *
	 * @author Yangcl
	 * @date 2017年11月17日 下午11:11:25 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_domain_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiDomainAdd(AddAcIncludeDomainRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_add", "添加跨域白名单");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiDomainAdd(param, session);  
	}
	/**
	 * @description: 编辑跨域白名单
	 *
	 * @author Yangcl
	 * @date 2017年11月18日 下午9:56:10 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_domain_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiDomainEdit(UpdateAcIncludeDomainRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_edit", "编辑跨域白名单");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiDomainEdit(param, session);  
	}
	
	/**
	 * @description: 删除一条跨域白名单记录
	 *
	 * @author Yangcl
	 * @date 2020年1月7日 上午10:20:16 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_domain_delete", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiDomainDelete(DeleteAcIncludeDomainRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_delete", "删除一条跨域白名单记录");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxBtnApiDomainDelete(param, session);  
	}
	
	//////////////////////////////////////////////////////////////////////////////【系统api信息】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: api信息树 
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月19日 下午2:33:26 
	 * @version 1.0.0
	 */
	@RequestMapping("page_apicenter_api_tree")  
	public String pageApicenterApiTree(HttpSession session){ 
		super.userBehavior(session, logger, "page_apicenter_api_tree", "前往api信息树页面");
		return "views/api/info/api-tree"; 
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
	@RequestMapping(value = "ajax_api_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<List<ApiTreeView>> ajaxApiInfoList(FindApiInfoListRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_list", "获取api树结构列表信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoList(param, session);  
	}
	
	/**
	 * @description: 添加api信息
	 *
	 * @param e
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月28日 下午3:19:55 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_api_info_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<AcApiInfo> ajaxApiInfoAdd(AddApiInfoRequest param, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_add", "添加api信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoAdd(param, session);  
	}
	
	/**
	 * @description: 依据target 查找一个api信息
	 *
	 * @author Yangcl
	 * @date 2017年11月29日 下午4:26:33 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_api_info_find", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<AcApiInfoCache> ajaxApiInfoFind(FindApiInfoRequest param, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_find", "依据target 查找一个api信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoFind(param);  
	}
	
	/**
	 * @description: 修改api信息 
	 *
	 * @author Yangcl
	 * @date 2017年11月30日 下午3:26:55 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_api_info_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<AcApiInfoCache> ajaxApiInfoEdit(UpdateApiInfoRequest param, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_edit", "修改api信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoEdit(param, session);  
	}
	
	/**
	 * @description: 删除一个API
	 *
	 * @author Yangcl
	 * @date 2020年1月10日 下午4:44:37 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_remove", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxBtnApiRemove(DeleteApiInfoRequest param, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_remove", "删除api信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoRemove(param, session);  
	}
	
	/**
	 * @description: 系统接口熔断：恢复启用|立刻熔断
	 *
	 * @author Yangcl
	 * @date 2020年1月13日 下午2:48:18 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_api_info_discard", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxApiInfoDiscard(UpdateApiInfoDiscardRequest param , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_discard", "恢复启用|立刻熔断api信息");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxApiInfoDiscard(param, session);  
	}
	
	//////////////////////////////////////////////////////////////////////////////【请求者信息维护】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 请求者信息维护页面
	 *
	 * @param session
	 * @return 
	 * @author Yangcl
	 * @date 2017年12月1日 上午10:42:52 
	 * @version 1.0.0
	 */
	@RequestMapping("page_apicenter_request_info")  
	public String requestInfoList(HttpSession session){ 
		super.userBehavior(session, logger, "page_apicenter_request_info", "请求者信息维护页面");
		return service.requestInfoList(); 
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
	@RequestMapping(value = "ajax_request_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<PageInfo<AcRequestInfoView>> ajaxRequestInfoList(FindRequestInfoListRequest param, HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_list", "ac_request_info 接口请求者列表分页数据");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxRequestInfoList(param, request, session);  
	}
	
	/**
	 * @description: ac_request_info添加数据
	 *
	 * @author Yangcl
	 * @date 2017年12月1日 下午1:42:20 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_request_info_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxRequestInfoAdd(AddRequestInfoRequest param, HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_add", "ac_request_info 接口请求者 添加数据");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxRequestInfoAdd(param, request, session);
	}
	
	/**
	 * @description: 编辑信息(organization & atype)|启用/禁用(flag)|为第三方调用者分配系统开放接口(open-api)
	 *
	 * @param dto
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月1日 下午2:21:07 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_request_info_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<?> ajaxRequestInfoEdit(UpdateRequestInfoRequest param, HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_edit", "ac_request_info 接口请求者 编辑数据");
		param.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return service.ajaxRequestInfoEdit(param, request, session);  
	}
	
	
	//////////////////////////////////////////////////////////////////////////////【接口测试】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 前往接口测试页面
	 *
	 * @author Yangcl
	 * @date 2017年12月11日 上午11:46:32 
	 * @version 1.0.0
	 */
	@RequestMapping("page_apicenter_api_test")  
	public String pageApicenterApiTest(ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "page_apicenter_api_test", "前往接口测试页面");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		model.addAttribute("date", sdf.format(new Date()));  
		
		return service.pageApicenterApiTest(); 
	}
	
	/**
	 * @description: 根据请求者的key，找到对应的value
	 *
	 * @param key
	 * @author Yangcl
	 * @date 2017年12月25日 下午10:11:23 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_find_request_value", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public Result<String> ajaxFindRequestValue(String key , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_find_request_value", "根据请求者的key，找到对应的value");
		return service.ajaxFindRequestValue(key);  
	}
}










































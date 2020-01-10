package com.matrix.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.dto.AcApiInfoDto;
import com.matrix.pojo.dto.AcRequestInfoDto;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.entity.AcApiProject;
import com.matrix.pojo.entity.AcIncludeDomain;
import com.matrix.pojo.entity.AcRequestInfo;
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
	public JSONObject ajaxApiProjectList(AcApiProject entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_project_list", "获取api所属项目列表页数据");
		return service.ajaxApiProjectList(entity, request);  
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
	public JSONObject ajaxBtnApiProjectAdd(AcApiProject entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_add", "向ac_api_project表添加信息");
		return service.ajaxBtnApiProjectAdd(entity, session);  
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
	public JSONObject ajaxBtnApiProjectEdit(AcApiProject entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_edit", "向ac_api_project表修改信息");
		return service.ajaxBtnApiProjectEdit(entity, session);  
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
	public JSONObject ajaxBtnApiProjectDelete(AcApiProject entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_project_delete", "向ac_api_project表删除一条记录");
		return service.ajaxBtnApiProjectDelete(entity, session);  
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
	 * @param entity
	 * @param request
	 * @author Yangcl
	 * @date 2017年11月15日 上午11:19:57 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_include_domain_page_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxIncludeDomainPageList(AcIncludeDomain entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_include_domain_page_list", "ac_include_domain 跨域白名单列表数据请求");
		return service.ajaxIncludeDomainPageList(entity, request, session);  
	}
	/**
	 * @description: 全量跨域白名单列表数据，不分页
	 *
	 * @param entity
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月27日 下午11:22:33 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_include_domain_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxIncludeDomainList(AcIncludeDomain entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_include_domain_list", "ac_include_domain 全量跨域白名单列表数据，不分页");
		return service.ajaxIncludeDomainList(entity, request, session);  
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
	@RequestMapping(value = "ajax_btn_api_domain_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnApiDomainAdd(AcIncludeDomain entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_add", "添加跨域白名单");
		return service.ajaxBtnApiDomainAdd(entity, session);  
	}
	/**
	 * @description: 编辑跨域白名单
	 *
	 * @param entity
	 * @param session
	 * @author Yangcl
	 * @date 2017年11月18日 下午9:56:10 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_btn_api_domain_edit", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxBtnApiDomainEdit(AcIncludeDomain entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_edit", "编辑跨域白名单");
		return service.ajaxBtnApiDomainEdit(entity, session);  
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
	public JSONObject ajaxBtnApiDomainDelete(AcIncludeDomain entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_btn_api_domain_delete", "删除一条跨域白名单记录");
		return service.ajaxBtnApiDomainDelete(entity, session);  
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
	public JSONObject ajaxApiInfoList(AcApiInfo e , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_list", "获取api树结构列表信息");
		return service.ajaxApiInfoList(e, session);  
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
	public JSONObject ajaxApiInfoAdd(AcApiInfoDto d , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_add", "添加api信息");
		return service.ajaxApiInfoAdd(d, session);  
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
	@RequestMapping(value = "ajax_api_info_find", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxApiInfoFind(AcApiInfoDto dto , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_find", "依据target 查找一个api信息");
		return service.ajaxApiInfoFind(dto);  
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
	public JSONObject ajaxApiInfoEdit(AcApiInfoDto d , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_edit", "修改api信息");
		return service.ajaxApiInfoEdit(d, session);  
	}
	
	/**
	 * @description: 删除一个API
	 *
	 * @author Yangcl
	 * @date 2020年1月10日 下午4:44:37 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_api_info_remove", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxApiInfoRemove(AcApiInfoDto d , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_api_info_remove", "删除api信息");
		return service.ajaxApiInfoRemove(d, session);  
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
	public JSONObject ajaxRequestInfoList(AcRequestInfo entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_list", "ac_request_info 接口请求者列表分页数据");
		return service.ajaxRequestInfoList(entity, request, session);  
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
	@RequestMapping(value = "ajax_request_info_add", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxRequestInfoAdd(AcRequestInfo entity , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_add", "ac_request_info 接口请求者 添加数据");
		return service.ajaxRequestInfoAdd(entity, request, session);  
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
	public JSONObject ajaxRequestInfoEdit(AcRequestInfoDto dto , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_request_info_edit", "ac_request_info 接口请求者 编辑数据");
		return service.ajaxRequestInfoEdit(dto, request, session);  
	}
	
	
	//////////////////////////////////////////////////////////////////////////////【接口测试】/////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * @description: 前往接口测试页面
	 *
	 * @param session
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
	 * @description: 根据接口target，返回查询消息体
	 *
	 * @param target
	 * @param request
	 * @param session
	 * @author Yangcl
	 * @date 2017年12月11日 下午4:57:09 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_find_request_dto", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindRequestDto(String target , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_find_request_dto", "根据接口target，返回查询消息体");
		return service.ajaxFindRequestDto(target);  
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
	@RequestMapping(value = "ajax_find_request_value", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxFindRequestValue(String key , HttpServletRequest request, HttpSession session){ 
		super.userBehavior(session, logger, "ajax_find_request_value", "根据请求者的key，找到对应的value");
		return service.ajaxFindRequestValue(key);  
	}
}










































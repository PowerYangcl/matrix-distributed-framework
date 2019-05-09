package com.matrix.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.pojo.entity.UserDemo;
import com.matrix.service.IExampleService;

/**
 * @descriptions 所有【示例】相关的后台方法都在这里 
 * 
 * @author Yangcl
 * @date 2016年5月28日-下午4:40:04
 * @version 1.0.0
 */
@Controller
@RequestMapping("example")
public class ExampleController extends BaseController{
	private static Logger logger=Logger.getLogger(ExampleController.class);
	
	@Autowired
	private IExampleService exampleService;
	
	
	/**
	 * @descriptions 前往添加页面 addExample.jsp
	 * 
	 * @author Yangcl
	 * @date 2016年6月13日-下午10:53:55
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_example_add_info")  
	public String toAddPage(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_add_info", "前往添加页面");
		return "jsp/example/addExample"; 
	}
	
	/**
	 * @descriptions 添加一条信息到数据库，成功后跳转回添加页面|并对密码做MD5加密处理
	 * 
	 * 页面传递到SpringMvc的时候会出现400错误，意味着参数类型与实体的不对应
	 * 数据库中是Data类型故实体类中的字段也是Date类型，但页面传的是String类型
	 * 所以才导致400错误。此时需要将Date类型的字段改成String类型此问题即可解决。
	 * 且不会影响数据的插入。
	 * 
	 * @author Yangcl
	 * @date 2016年6月13日-下午11:04:45
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_add_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addInfo(UserDemo entity , HttpSession session){ 
		super.userBehavior(session, logger, "ajax_add_info", "添加一条信息到数据库");
		return exampleService.addInfo(entity, session);  
	}
	
	 
	/**
	 * @descriptions 简单分页示例 Ajax分页 不涉及弹窗分页问题 
	 * 
	 * @date 2016年8月17日下午5:44:52
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_example_ajax_form")
	public String toAjaxFormExample(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_ajax_form", "简单分页示例 Ajax分页 不涉及弹窗分页问题");
		return "jsp/example/ajaxFormExample"; 
	}
	
	@RequestMapping(value = "ajax_page_data", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxPageData(UserDemo entity , HttpServletRequest request, HttpSession session){
		super.userBehavior(session, logger, "ajax_page_data", "获取数据列表");
		return exampleService.ajaxPageData(entity, request);  
	}
	
	
	/**
	 * @descriptions Ajax分页 且弹窗同时分页
	 * 
	 * @date 2016年8月17日下午5:44:52
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_example_ajax_form_dialog")
	public String ajaxFormDialogExample(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_ajax_form_dialog", "Ajax分页 且弹窗同时分页");
		return "jsp/example/ajaxFormDialogExample"; 
	}
	
	
	
	/**
	 * @descriptions 自定义 alert confirm note
	 * 
	 * @param session 
	 * @date 2016年8月22日上午11:43:59
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */ 
	@RequestMapping("page_example_alert")
	public String alertExample(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_alert", "自定义 alert confirm note");
		return "jsp/example/alertExample"; 
	}

	
	@RequestMapping(value = "ajax_delete_one", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject deleteOne(UserDemo info , HttpSession session){
		super.userBehavior(session, logger, "ajax_delete_one", "删除一条记录");
		return exampleService.deleteOne(info); 
	}
	
	
	/**
	 * @description: 内部跳转页 编辑页
	 * 
	 * @author Yangcl 
	 * @date 2017年5月25日 下午4:28:02 
	 * @version 1.0.0.1
	 */
	@RequestMapping("edit_info_page")   
	public String editInfoPage(UserDemo info , ModelMap model , HttpServletRequest request, HttpSession session){
		super.userBehavior(session, logger, "edit_info_page", "列表页内部跳转，修改一条记录");
		return "jsp/example/editExample"; 
	}
	
	
	@RequestMapping(value = "ajax_edit_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editInfo(UserDemo info){
		return null;
	}
	
	
	
	/**
	 * @descriptions 实际样本-A
	 *  
	 * @date 2016年11月28日 上午10:32:24 
	 * @author Yangcl 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "page_example_a")
	public String example_a(HttpSession session) { 
		super.userBehavior(session, logger, "page_example_a", "实际样本-A"); 
		return "jsp/example/reality/questionQuery";
	}

	/**
	 * @description: 实际样本-B
	 * 
	 * @return
	 * @author Yangcl 
	 * @date 2016年11月28日 上午10:32:24 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "page_example_b")
	public String example_b(HttpSession session) { 
		super.userBehavior(session, logger, "page_example_b", "实际样本-B"); 
		return "jsp/example/reality/validate";
	}
	
	// 实际样本-B 
	@RequestMapping(value = "example_b1")
	public String example_b1(String key, HttpSession session) { 
		super.userBehavior(session, logger, "example_b1", "实际样本-B1"); 
		if(key.equals("whosyourdaddy")){ 
			session.setAttribute("kjt-key", "kjt-key"); // 写入session
			return "redirect:/jsp/example/reality/index.jsp";    
		}else{ 
			return "redirect:/jsp/example/reality/validate.jsp";
		} 
	}
	
	// 离开此页面
	@RequestMapping(value = "leave")
	public String leave(HttpSession session ) { 
		session.setAttribute("kjt-key", null); // 删除session
		return "redirect:/jsp/example/reality/validate.jsp";    
	}
	
	@RequestMapping("page_example_block_ui")
	public String blockUiPage(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_b", "blockUi Page Example"); 
		return "jsp/example/blockUiPageExample"; 
	}
	
	/**
	 * @description: ueditor编辑器测试页面
	 * 
	 * @param session
	 * @return
	 * @author Yangcl 
	 * @date 2017年6月8日 上午11:19:07 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_example_ueditor")   
	public String ueditorPage(HttpSession session){
		super.userBehavior(session, logger, "page_example_ueditor", "ueditor编辑器测试页面");
		return "jsp/example/ueditorExample"; 
	}
	
	
	/**
	 * @description: 针对UE，采用自定义的上传图片方式|
	 * 	此种方式使用cfile接口将图片上传到图片服务器
	 * 
	 * @param type uploadimage:上传图片|uploadfile:上传文件
	 * @param request
	 * @param resonse
	 * @return
	 * @author Yangcl 
	 * @date 2017年6月8日 下午3:21:48 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_upload_file_cfile", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject ajaxUploadFileCfile(String type , HttpServletRequest request, HttpServletResponse resonse , HttpSession session){
		super.userBehavior(session, logger, "ajax_upload_file_cfile", "针对UE，采用自定义的上传图片方式 此种方式使用cfile接口将图片上传到图片服务器");
		return exampleService.ajaxUploadFileCfile(type , request); 
	}
	
	
	/**
	 * @description: 图片与文件上传应用示例
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年10月20日 下午2:35:39 
	 * @version 1.0.0
	 */
	@RequestMapping("page_example_file_upload")
	public String pageExampleFileUpload(HttpSession session){ 
		super.userBehavior(session, logger, "page_example_file_upload", "图片与文件上传应用示例");
		return "jsp/example/pageExampleFileUpload"; 
	}
	
	/**
	 * @description: 开发者规约 - java
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年10月27日 下午3:42:32 
	 * @version 1.0.0
	 */
	@RequestMapping("page_developer_specification")
	public String pageDeveloperSpecification(HttpSession session){
		super.userBehavior(session, logger, "page_developer_specification", "开发者规约-java界面page-developer-specification.jsp");
		return "jsp/readme/page-developer-specification";
	}
	
	/**
	 * @description: 开发者规约 - javascript
	 *
	 * @param session
	 * @author Yangcl
	 * @date 2017年10月27日 下午3:42:32 
	 * @version 1.0.0
	 */
	@RequestMapping("page_developer_specification_js")
	public String pageDeveloperSpecificationJs(HttpSession session){
		super.userBehavior(session, logger, "page_developer_specification_js", "开发者规约-javascript界面page-developer-specification-js.jsp");
		return "jsp/readme/page-developer-specification-js";
	}
	
	/**
	 * @description: http client 请求测试。HttpClientSupportTest.java将会调用此接口。api_http_client_test接口将会从
	 * 		请求的request对象中取出二进制文本中的字符串信息
	 *
	 * @param request
	 * @author Yangcl
	 * @date 2017年11月6日 下午5:09:38 
	 * @version 1.0.0
	 */
	@RequestMapping(value = "ajax_api_http_client_test", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject apiHttpClientTest(HttpServletRequest request){
		return exampleService.apiHttpClientTest(request);
	} 
	
}
































package com.matrix.controller;

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
import com.matrix.pojo.dto.CompanyInfoDto;
import com.matrix.pojo.dto.CompanyPaymentInfoDto;
import com.matrix.pojo.entity.CompanyInfo;
import com.matrix.pojo.entity.CompanyPaymentInfo;
import com.matrix.pojo.view.McUserInfoView;
import com.matrix.service.ICompanyInfoService;
import com.matrix.service.ICompanyPaymentInfoService;

/** @description: 公司信息操作
 *
 * @author wanghao
 * @date 2018年11月5日 下午2:27:41 
 * @version 1.0.0.1
 */
@Controller
@RequestMapping("company")
public class CompanyInfoController extends BaseController{
	private static Logger logger = Logger.getLogger(CompanyInfoController.class);
	
	@Autowired
	private ICompanyInfoService companyInfoService;
	@Autowired
	private ICompanyPaymentInfoService companyPaymentInfoService;
	/**
	 * @description: 公司信息列表跳转
	 *
	 * @param model
	 * @param session
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午5:36:20 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_company_info_list") 
	public String pageCompanyInfoList(ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "page_company_info_list", "前往租户列表页面sysMcRoleList.jsp");
		return "jsp/syssetting/company/companyInfoList";   
	}
	
	/**
	 * @description: 公司信息分页列表数据
	 *
	 * @param dto
	 * @param session
	 * @param request
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午5:35:58 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "company_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject companyInfoList(CompanyInfoDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "company_info_list", "系统公司信息列表数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return companyInfoService.pageListByDto(dto, request);
	}
	/**
	 * @description: 公司信息添加页面跳转
	 *
	 * @param model
	 * @param session
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午7:23:53 
	 * @version 1.0.0.1
	 */
	@RequestMapping("company_info_add_page") 
	public String companyInfoAddPage(ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "company_info_add_page", "前往公司信息添加页面companyInfoAdd.jsp");
		return "jsp/syssetting/company/companyInfoAdd";   
	}
	/**
	 * @description: 添加公司信息
	 *
	 * @param dto
	 * @param session
	 * @param request
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午7:26:31 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "add_company_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addCompanyInfo(CompanyInfo e , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "add_company_info", "系统公司信息添加数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyInfoService.addCompanyInfo(e);
		return result;
	}
	/**
	 * @description: 公司信息编辑页面跳转
	 *
	 * @param model
	 * @param session
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午7:23:53 
	 * @version 1.0.0.1
	 */
	@RequestMapping("company_info_edit_page") 
	public String redirectCompanyInfoEdit(Long id ,ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "company_info_edit_page", "前往公司信息编辑页面companyInfoEdit.jsp");
		CompanyInfo entity = companyInfoService.find(id);
		if(entity != null){
			model.addAttribute("entity", entity);  
		}
		return "jsp/syssetting/company/companyInfoEdit";   
	}
	
	
	/**
	 * @description: 编辑公司信息
	 *
	 * @param dto
	 * @param session
	 * @param request
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:03:35 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "edit_company_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editCompanyInfo(CompanyInfo e, HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "edit_company_info", "系统公司信息编辑数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyInfoService.editCompanyInfo(e);
		return result;
	}
	
	/**
	 * @description: 删除公司信息
	 *
	 * @param dto
	 * @param session
	 * @param request
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:03:35 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "del_company_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject delCompanyInfo(CompanyInfo e , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "del_company_info", "系统公司信息删除数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyInfoService.delCompanyInfo(e);
		return result;
	}
	/**
	 * @description: 公司信息查询
	 *
	 * @param e
	 * @param session
	 * @param request
	 * @return 
	 * @author wanghao
	 * @date 2018年11月5日 下午8:16:06 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "get_company_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject getCompanyInfo(CompanyInfoDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "get_company_info", "系统公司信息查询数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result = companyInfoService.getCompanyInfo(dto);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping("company_payment_info_list_page") 
	public String companyPaymentInfoListPage(Long cid,ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "company_payment_info_list", "前往租户支付列表页面sysMcRoleList.jsp");
		CompanyInfo companyInfo = companyInfoService.find(cid);
		model.addAttribute("companyInfo", companyInfo);  
		return "jsp/syssetting/company/companyPaymentInfoList";   
	}
	
	
	@RequestMapping(value = "company_payment_info_list", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject companyPaymentInfoList(CompanyPaymentInfoDto dto , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "company_payment_info_list", "系统公司支付信息列表数据");
		dto.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		return companyPaymentInfoService.pageListByDto(dto, request);
	}
	
	@RequestMapping("company_payment_info_add_page") 
	public String companyPaymentInfoAddPage(Long cid,ModelMap model , HttpSession session){ 
		CompanyInfo companyInfo = companyInfoService.find(cid);
		model.addAttribute("companyInfo", companyInfo);  
		super.userBehavior(session, logger, "company_payment_info_add_page", "前往公司支付信息添加页面companyInfoAdd.jsp");
		return "jsp/syssetting/company/companyPaymentInfoAdd";   
	}
	
	@RequestMapping(value = "add_company_payment_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject addCompanyPaymentInfo(CompanyPaymentInfo e , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "add_company_payment_info", "系统公司支付信息添加数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyPaymentInfoService.addCompanyPaymentInfo(e);
		return result;
	}
	
	@RequestMapping("company_payment_info_edit_page") 
	public String companyPaymentInfoEditPage(Long id ,ModelMap model , HttpSession session){ 
		super.userBehavior(session, logger, "company_payment_info_edit_page", "前往公司支付信息编辑页面companyInfoEdit.jsp");
		CompanyPaymentInfo entity = companyPaymentInfoService.find(id);
		if(entity != null){
			model.addAttribute("entity", entity);  
			CompanyInfo companyInfo = companyInfoService.find(entity.getCid());
			model.addAttribute("companyInfo", companyInfo);  
		}
		
		return "jsp/syssetting/company/companyPaymentInfoEdit";   
	}
	
	
	
	@RequestMapping(value = "edit_company_payment_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject editCompanyPaymentInfo(CompanyPaymentInfo e, HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "edit_company_payment_info", "系统公司支付信息编辑数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyPaymentInfoService.editCompanyPaymentInfo(e);
		return result;
	}
	
	
	
	
	@RequestMapping(value = "del_company_payment_info", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject delCompanyPaymentInfo(CompanyPaymentInfo e , HttpSession session , HttpServletRequest request) {
		super.userBehavior(session, logger, "del_company_payment_info", "系统公司信息删除数据");
		e.setUserCache((McUserInfoView) session.getAttribute("userInfo"));
		JSONObject result  = companyPaymentInfoService.delCompanyPaymentInfo(e);
		return result;
	}
	
	
}

package com.matrix.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseServiceImpl;
import com.matrix.dao.ITcLandedPropertyMapper;
import com.matrix.pojo.dto.TcLandedPropertyDto;
import com.matrix.pojo.entity.TcLandedProperty;
import com.matrix.pojo.view.TcLandedPropertyView;
import com.matrix.service.ITcLandedPropertyService;

@Service("tcLandedPropertyService")
public class TcLandedPropertyServiceImpl extends BaseServiceImpl<Long , TcLandedProperty, TcLandedPropertyDto , TcLandedPropertyView> implements ITcLandedPropertyService {

	@Resource
	private ITcLandedPropertyMapper tcLandedPropertyMapper;

	@Override
	public String pageDemoLandedProperty(ModelMap model) {  
		// 由service内部返回跳转页面
		return "jsp/example/demo/page-landed-property-list";
	}

	/**
	 * @description: 针对 “this.ajaxPageData(entity, request); ”这句代码的解释如下：
	 * 		请注意！如果传递的参数不是：TcLandedProperty entity，而是一个特殊业务场景下，
	 * 		结合了多张表的查询条件，形成的一个DTO数据传输模型，建议你在自己的service层
	 * 		方法中重写BaseServiceImpl.java中的ajaxPageData()方法。
	 * 		ApiCenterServiceImpl.java中有大量的重写列表页的方法，你可以参考，比如其中的
	 * 		一个方法：public JSONObject ajaxApiProjectList(AcApiProject entity, HttpServletRequest request, HttpSession session) 
	 * 		重写的方法能够表达更多、更灵活的信息。
	 * 
	 * 		最后，不要忘记在你的mapper.xml配置文件中加入对应的sql查询语句：queryPage(如果你直接使用的this.ajaxPageData方法，自定义的方法自己写)。
	 *
	 * @param entity
	 * @author Yangcl
	 * @date 2018年2月21日 下午3:27:04 
	 * @version 1.0.0.1
	 */
	@Override
	public JSONObject ajaxDemoLandedPropertyList(TcLandedProperty entity, HttpServletRequest request, HttpSession session) {
		return this.ajaxPageData(entity, request); 
	}
	
	
	
	
}
































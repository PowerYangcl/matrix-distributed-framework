package com.matrix.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.interfaces.IBaseService;
import com.matrix.pojo.dto.TcLandedPropertyDto;
import com.matrix.pojo.entity.TcLandedProperty;
import com.matrix.pojo.view.TcLandedPropertyView;

public interface ITcLandedPropertyService  extends IBaseService<Long , TcLandedProperty, TcLandedPropertyDto , TcLandedPropertyView>{

	public String pageDemoLandedProperty(ModelMap model); 

	public JSONObject ajaxDemoLandedPropertyList(TcLandedProperty entity, HttpServletRequest request, HttpSession session); 

}

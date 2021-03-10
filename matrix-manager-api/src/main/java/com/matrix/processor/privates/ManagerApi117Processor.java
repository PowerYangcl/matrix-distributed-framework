package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 更新拖拽后的同层节点|dto.ustring id@seqnum,id@seqnum 
 * @tag MANAGER-API-117
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McSysFunctionDto.class)
public class ManagerApi117Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;  
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McSysFunctionDto dto = JSONObject.parseObject(param.getString("data"), McSysFunctionDto.class);
		return mcSysFunctionService.updateTreeNodes(dto);
	}

}

















































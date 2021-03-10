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
 * @description: 获取树列表|sys-user-role-function.js使用2次
 * @tag MANAGER-API-109
 * 
 * @param dto.platform 如果不为空则获取指定平台下的功能节点
 * @param dto.type type=list or role|如果type=role则同时获得角色列表，同时dto.id = roleId
 * 
 * @author Yangcl 
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */

/**
 * 
 * request 实体   McSysFunctionDto
 * response返回类型
   {
	"list": [{
			"createTime": "2018-09-21 14:32:46",
			"createUserId": 1,
			"createUserName": "admin",
			"updateTime": "2018-09-21 14:28:59",
			"updateUserId": 1,
			"updateUserName": "admin",
			"deleteFlag": 1,
			"userCache": null,
			"id": 341,
			"mcSellerCompanyId": 1,
			"name": "会员平台",
			"parentId": "1",
			"seqnum": 2,
			"navType": 0,
			"platform": "133EFB922DF3",
			"styleClass": "",
			"styleKey": "",
			"funcUrl": "",
			"remark": "会员平台标识",
			"btnArea": "",
			"eleValue": "",
			"open": true
		}
	}], "status": "success"
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McSysFunctionDto.class)
public class ManagerApi109Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McSysFunctionDto dto = JSONObject.parseObject(param.getString("data"), McSysFunctionDto.class);
		return mcSysFunctionService.treeList(dto);
	}

}





































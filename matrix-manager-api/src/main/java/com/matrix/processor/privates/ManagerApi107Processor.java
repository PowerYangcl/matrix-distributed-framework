package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McUserInfoDto;
import com.matrix.service.IMcUserInfoService;

/**
 * @description: 删除一个用户|不保留数据库中的记录
 * @tag MANAGER-API-107
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */

/**
 * 
 * request 实体   McUserInfoDto
 * {
	 "id": "2001"
   }
   response返回类型
   {"msg":"删除成功","status":"success"}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi107Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserInfoDto dto = JSONObject.parseObject(param.getString("data"), McUserInfoDto.class);
		return mcUserInfoService.deleteUser(dto);
	}

}

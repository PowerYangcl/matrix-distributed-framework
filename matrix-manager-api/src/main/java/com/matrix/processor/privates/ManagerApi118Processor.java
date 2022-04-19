package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseApiDto;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.request.DeleteMcSysFunctionRequest;
import com.matrix.service.IMcSysFunctionService;

/**
 * @description: 删除一个系统功能节点及其子节点
 * @tag MANAGER-API-118
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.DeleteMcSysFunctionRequest.class)
public class ManagerApi118Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;  
	
	@Override
	public Result<?> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		DeleteMcSysFunctionRequest dto = param.getData().toJavaObject(DeleteMcSysFunctionRequest.class);
		dto.setUserCache(param.getUserCache());
		return mcSysFunctionService.deleteNode(dto);
	}

}










































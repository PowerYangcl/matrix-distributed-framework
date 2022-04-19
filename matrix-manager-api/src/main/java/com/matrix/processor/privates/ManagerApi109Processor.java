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
import com.matrix.pojo.request.FindTreeListRequest;
import com.matrix.pojo.view.TreeListView;
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
@MatrixRequest(clazz=com.matrix.pojo.request.FindTreeListRequest.class)
public class ManagerApi109Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcSysFunctionService mcSysFunctionService;   
	
	@Override
	public Result<TreeListView> processor(BaseApiDto param, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FindTreeListRequest dto = param.getData().toJavaObject(FindTreeListRequest.class);
		return mcSysFunctionService.treeList(dto);
	}

}





































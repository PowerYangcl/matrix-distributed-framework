package com.matrix.processor.privates;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.service.IMcRoleService;

/**
 * @description: 系统角色列表数据
 * @tag MANAGER-API-110
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * request 参数实体 McRoleDto
 * {
	"roleName": "角色名称"--，模糊查询
	}
  respose 返回参数
 	"list": 
 		[
        {
        "createTime": "2018-10-18 14:57:36",
        "createUserId": 1993,
        "createUserName": "admin-lqx",
        "updateTime": "2018-10-23 19:52:36",
        "updateUserId": 1993,
        "updateUserName": "admin-lqx",
        "deleteFlag": 1,
        "userCache": null,
        "id": 62,
        "cid": 2,
        "type": "admin",
        "platform": "133EFB922DF3",
        "roleName": "会员管理-隆庆祥",
        "roleDesc": "会员管理-隆庆祥",
        "remark": "",
        "userId": -1,
        "platformName": "会员平台"
        }
    ],
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi110Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcRoleService mcRoleService;
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McRoleDto dto = JSONObject.parseObject(param.getString("data"), McRoleDto.class);
		return mcRoleService.ajaxSystemRoleList(dto , request);
	}

}




































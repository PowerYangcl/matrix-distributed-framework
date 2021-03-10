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
 * @description: 获取用户详情
 * @tag MANAGER-API-105
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体   id
 * {
	 "userName": "62"，
	 "mobile": "182351188543"
   }
   response返回类型
   {
    "msg": "添加成功",
    "entity": {
        "createTime": "2018-10-24 17:04:30",
        "createUserId": 1993,
        "createUserName": "admin-lqx",
        "updateTime": "2018-10-24 17:04:30",
        "updateUserId": 1993,
        "updateUserName": "admin-lqx",
        "deleteFlag": 1,
        "userCache": null,
        "id": 2002,
        "cid": 2,
        "userName": "13641250843",
        "password": "ccc73e1272b19ab81c9d82550103b395",
        "type": "user",
        "idcard": "",
        "sex": 1,
        "birthday": null,
        "mobile": "13641250843",
        "email": "mashaohua@300.cn",
        "remark": "121"
    },
    "status": "success"
}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi105Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserInfoDto dto = JSONObject.parseObject(param.getString("data"), McUserInfoDto.class);
		return mcUserInfoService.ajaxFindSysUser(dto);
	}

}

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
 * @description: 用户列表页数据展示
 * @tag MANAGER-API-102
 *
 * @author Yangcl
 * @date 2018年10月10日 下午7:19:45 
 * @version 1.0.0.1
 */
/**
 * 
 * request 实体   McUserInfoDto
 * {
	 "userName": "62"，
	 "mobile": "182351188543"
   }
   response返回类型
  {
    "data": {
        "pageNum": 1,
        "pageSize": 10,
        "size": 3,
        "orderBy": null,
        "startRow": 1,
        "endRow": 3,
        "total": 3,
        "pages": 1,
        "list": [
            {
                "createTime": "2018-10-23 19:23:31",
                "createUserId": null,
                "createUserName": null,
                "updateTime": null,
                "updateUserId": null,
                "updateUserName": null,
                "deleteFlag": 1,
                "userCache": null,
                "id": 2001,
                "cid": 2,
                "userName": "18614036827",
                "password": "46f94c8de14fb36680850768ff1b7f2a",
                "type": "user",
                "idcard": "",
                "sex": 1,
                "birthday": null,
                "mobile": "18200000004",
                "email": "mashaohua@300.cn",
                "remark": "11111",
                "picUrl": "",
                "pageCss": "default",
                "platform": "133EFB922DF3"
            },
    "status": "success"
}
 * @author mashaohua
 *
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McUserInfoDto.class)
public class ManagerApi102Processor extends BaseClass implements IBaseProcessor {

	
	@Inject
	private IMcUserInfoService mcUserInfoService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		McUserInfoDto dto = JSONObject.parseObject(param.getString("data"), McUserInfoDto.class);
		return mcUserInfoService.ajaxSystemUserList(dto , request);
	}

}




















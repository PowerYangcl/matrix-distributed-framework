package com.matrix.processor.privates;/**
 * Created by mashaohua on 2018/12/19.
 */

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.McRoleDto;
import com.matrix.service.IMcRoleService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName:ManagerApi142Processor
 * @Description:获取用户的角色（用户登录以后查自身的角色）
 * @Author:msahohua
 * @Date:2018/12/1918:52
 * @Vesion: 1.0
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi142Processor extends BaseClass implements IBaseProcessor {

    @Inject
    private IMcRoleService mcRoleService;


    @Override
    public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
        McRoleDto dto = JSONObject.parseObject(param.getString("data"), McRoleDto.class);
        return mcRoleService.userRoleListByid(dto , request);
    }
}

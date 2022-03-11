package com.matrix.processor.privates;/**
 * Created by mashaohua on 2018/12/19.
 */

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 获取用户的角色（用户登录以后查自身的角色）|TODO 接口功能已移除，等待另作他用
 * 
 * @author Yangcl
 * @date 2021-6-9 16:01:03
 * @home https://github.com/PowerYangcl
 * @path matrix-manager-api / com.matrix.processor.privates.ManagerApi142Processor.java
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.dto.McRoleDto.class)
public class ManagerApi142Processor extends BaseClass implements IBaseProcessor {

    @Override
    public Result<?> processor(HttpServletRequest request, 
    		HttpServletResponse response, HttpSession session, JSONObject param) {
        return null;
    }
}

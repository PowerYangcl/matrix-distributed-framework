package com.matrix.processor.privates;


import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.request.FindStoreListRequest;
import com.matrix.pojo.view.McOrganizationView;
import com.matrix.service.IMcOrganizationService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 公司组织架树下的区域门店
 * @tag MANAGER-API-124
 * 
 * @author Yangcl
 * @date 2018年12月17日 下午7:19:45
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=com.matrix.pojo.request.FindStoreListRequest.class)
public class ManagerApi141Processor extends BaseClass implements IBaseProcessor {
    @Inject
    private IMcOrganizationService mcOrganizationService;

    @Override
    public Result<List<McOrganizationView>> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
    	FindStoreListRequest dto = JSONObject.parseObject(param.getString("data"), FindStoreListRequest.class);
        return mcOrganizationService.ajaxStoreList(dto);
    }
}

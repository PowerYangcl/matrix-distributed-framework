package com.matrix.processor.privates;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.IBaseProcessor;
import com.matrix.base.Result;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.pojo.entity.StoreInfo;
import com.matrix.pojo.request.FindStoreInfoRequest;
import com.matrix.service.IStoreInfoService;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 门店列表数据
 * @tag MANAGER-API-131
 *
 * @author Yangcl
 * @date 2018年11月20日 下午7:19:45
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=StoreInfoDto.class)
public class ManagerApi131Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IStoreInfoService storeInfoService;
	
	@Override
	public Result<List<StoreInfo>> processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		FindStoreInfoRequest dto = JSONObject.parseObject(param.getString("data"), FindStoreInfoRequest.class);
		return storeInfoService.storeInfoList(dto);
	}

}































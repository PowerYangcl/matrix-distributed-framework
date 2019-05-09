package com.matrix.processor.privates;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.annotation.MatrixRequest;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseProcessor;
import com.matrix.pojo.dto.StoreInfoDto;
import com.matrix.service.IStoreInfoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 门店列表数据
 * @tag MANAGER-API-131
 *
 * @author 王聚
 * @date 2018年11月20日 下午7:19:45
 * @version 1.0.0.1
 */
@MatrixRequest(clazz=StoreInfoDto.class)
public class ManagerApi131Processor extends BaseClass implements IBaseProcessor {

	@Inject
	private IStoreInfoService storeInfoService;
	
	@Override
	public JSONObject processor(HttpServletRequest request, HttpServletResponse response, HttpSession session, JSONObject param) {
		StoreInfoDto dto = JSONObject.parseObject(param.getString("data"), StoreInfoDto.class);
		return storeInfoService.storeInfoList(dto);
	}

}































package com.matrix.load;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IAcApiDomainMapper;
import com.matrix.dao.IAcApiInfoMapper;
import com.matrix.dao.IAcIncludeDomainMapper;
import com.matrix.pojo.entity.AcApiInfo;
import com.matrix.pojo.view.AcApiDomainView;

public class ApiInfoInit  extends BaseClass implements ILoadCache<String>{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IAcIncludeDomainMapper acIncludeDomainMapper;
	@Inject
	private IAcApiDomainMapper acApiDomainMapper;
	@Inject
	private IAcApiInfoMapper acApiInfoMapper;
	
	/**
	 * @description: 多表联查，加载缓存
	 * key: xd-ApiInfo-ORDER-INFO
	 * value:
					{
						"discard": 1,
						"createUserId": 1992,
						"updateUserId": 20072026234128,
						"seqnum": 3,
						"module": "matrix-manager-api",
						"updateUserName": "XuTao",
						"pageSize": 10,
						"createUserName": "Yangcl",
						"remark": "验证用户登录信息|客户端用户：nodejs/IOS平板等",
						"updateTime": 1568786995000,
						"login": 0,
						"list": [],
						"dtoInfo": "{\"userName\":\"admin-mdl\",\"password\":\"123456\",\r\n\"platform\":\"134160222D87\"}",
						"processor": "privates.ManagerApi100Processor",
						"parentId": 9,
						"target": "MANAGER-API-100",
						"deleteFlag": 1,
						"startIndex": 1,
						"createTime": 1539244178000,
						"atype": "private",
						"domain": 0,
						"name": "用户登录接口",
						"id": 80160155
					}
	 * @param key  AcApiInfo.target
	 * @param field null
	 * @return 
	 * @author Yangcl
	 * @date 2017年11月29日 上午11:52:39 
	 * @version 1.0.0
	 */
	@Override
	public String load(String key, String field) {
		if(StringUtils.isBlank(key)) {
			return "";
		}
		AcApiInfo dto = new AcApiInfo();
		dto.setTarget(key); 
		List<AcApiInfo> apiInfoList = acApiInfoMapper.selectByEntity(dto); 
		if(apiInfoList == null || apiInfoList.size() == 0) {
			return "";
		}
		AcApiInfo e = apiInfoList.get(0);
		// 开始初始化API缓存
		JSONObject cache = JSONObject.parseObject(JSONObject.toJSONString(e)); 
		cache.put("list", new ArrayList<String>()); 
		if(e.getDomain() == 1) {		// 针对可跨域情况
			List<AcApiDomainView> adList = acApiDomainMapper.selectByApiInfoId(e.getId());
			if(adList != null && adList.size() != 0) {
				List<JSONObject> domains = new ArrayList<JSONObject>();
				List<String> list = new ArrayList<String>();
				for(AcApiDomainView v : adList) {
					JSONObject include = new JSONObject();
					include.put("id", v.getId());
					include.put("domain", v.getDomain());
					include.put("companyName", v.getCompanyName());
					domains.add(include);
					list.add(v.getDomain());
				}
				cache.put("domains", domains); 
				cache.put("list", list); 
			}
		}
		String value = cache.toJSONString();
		launch.loadDictCache(DCacheEnum.ApiInfo , null).set(key , value , 24*60*60); 
		return value;
	}

}




























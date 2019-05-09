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

public class InitApiInfo  extends BaseClass implements ILoadCache<String>{
	
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
					    "id": 80160001,
					    "name": "订单信息",
					    "target": "ORDER-INFO",
					    "discard": 0,
					    "login":1,                                           ---------------- 当前接口是否需要登录后访问：1 需要登录后访问 0不需要
					    "createUserId": 1,
					    "updateUserId": 1,
					    "seqnum": 1,
					    "module": "matrix-api",
					    "remark": "ORDER-INFO",
					    "updateTime": 1512032208000,
					    "list": [
					        "http://api.baidu.com",
					        "http://sub.model.firos.com.cn"
					    ],
					    "processor": "private.order.OrderInfomation",
					    "parentId": 1,
					    "domainIds": [
					        "4",
					        "5"
					    ],
					    "createTime": 1511922954000,
					    "atype": "private",
					    "domain": 1
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
		cache.put("domainIds", new ArrayList<String>()); 
		if(e.getDomain() == 1) {		// 针对可跨域情况
			List<AcApiDomainView> adList = acApiDomainMapper.selectByApiInfoId(e.getId());
			if(adList != null && adList.size() != 0) {
				List<String> ids = new ArrayList<>();
				List<String> domains = new ArrayList<>();
				for(AcApiDomainView v : adList) {
					ids.add(v.getId().toString());
					domains.add(v.getDomain());
				}
				cache.put("list", domains); 
				cache.put("domainIds", ids); 
			}
		}
		String value = cache.toJSONString();
		launch.loadDictCache(DCacheEnum.ApiInfo , null).set(key , value , 30*24*60*60); 
		return value;
	}

}




























package com.matrix.load;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IAcRequestInfoMapper;
import com.matrix.dao.IAcRequestOpenApiMapper;
import com.matrix.pojo.entity.AcRequestInfo;
import com.matrix.pojo.view.AcRequestOpenApiView;
/**
 * @description: init  ApiRequestKey to cache
 * key: xd-ApiRequester-133C9C129D53
 * value:
				 {
				    "key": "133C9C129D53",     -------------------- 接口调用方公钥
				    "value": "6DFA608D49324E47A5D69A13523BDFDA",     -------------------- 接口调用方私钥 
				    "flag": 1,     -------------------- 接口调用方状态：启用1 禁用 0
				    "atype": "private",     -------------------- 接口调用方分类。private:开放给公司内部接口请求者| public:公开，即注册给第三方的接口请求者
				    "organization": "IOS乘客端",
				    "createUserId": 1,
				    "createTime": 1512119626795,
				    "updateUserId": 1,
				    "updateTime": 1512119627160,
				    "id": 1,
				    "list": [] -------------------- 如果atype=public，此处应有对其开放的接口访问标识列表
				}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午9:34:34 
 * @version 1.0.0.1
 */
public class ApiRequesterInit extends BaseClass implements ILoadCache<String> {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IAcRequestInfoMapper acRequestInfoMapper; 
	@Inject
	private IAcRequestOpenApiMapper acRequestOpenApiMapper;

	@Override
	public String load(String key, String field) {
		AcRequestInfo e = acRequestInfoMapper.findByKey( key );
		if(e == null) {
			return "";
		}
		
		JSONObject cache = JSONObject.parseObject(JSONObject.toJSONString(e)); 
		if(e.getAtype().equals("public")) {
			List<AcRequestOpenApiView> list = acRequestOpenApiMapper.findListById(e.getId());
			if(list == null || list.size() == 0) {
				cache.put("list", new ArrayList<String>()); 
			}else {
				List<String> list_ = new ArrayList<String>();
				for(AcRequestOpenApiView v : list) {
					list_.add(v.getTarget());
				}
				cache.put("list", list_); 
			}
		}else {
			cache.put("list", new ArrayList<String>());   
		}
		String value = cache.toJSONString();
		launch.loadDictCache(DCacheEnum.ApiRequester , null).set(e.getKey() , value); 
		
		return value;
	}

}






































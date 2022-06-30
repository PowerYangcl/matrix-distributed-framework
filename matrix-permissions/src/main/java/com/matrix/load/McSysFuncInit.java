package com.matrix.load;


import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.entity.McSysFunction;
/**
 * @description: 如果缓存取值为空则此处做处理
 * key: xd-McSysFunc-75
 * value: 
				{
					"mcSellerCompanyId": 1,
					"seqnum": 2,
					"navType": 3,
					"eleValue": "",
					"remark": "Dubbo应用服务部署节点列表",
					"btnArea": "",
					"styleClass": "",
					"parentId": "465",
					"platform": "133C9CB27E18",
					"funcUrl": "route/page_route_dubbo_node_list.do",
					"name": "部署节点列表",
					"id": 510,
					"styleKey": ""
				}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午9:49:23 
 * @version 1.0.0.1
 */
public class McSysFuncInit extends BaseClass implements ILoadCache<String> {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcSysFunctionMapper mcSysFunctionMapper; 
	
	public String load(String key, String field) {
		try{
			McSysFunction e = mcSysFunctionMapper.find(Long.parseLong(key));
			if(e == null) {
				return "";
			}
			e.setUserCache(null);
			e.setUpdateTime(null);
			e.setUpdateUserId(null);
			e.setUpdateUserName(null);
			e.setCreateTime(null);
			e.setCreateUserId(null);
			e.setCreateUserName(null);
			e.setDeleteFlag(null);
			e.setPageSize(null);
			e.setStartIndex(null);
			String value = JSONObject.toJSONString(e); 
			launch.loadDictCache(CachePrefix.McSysFunc , null).set(key , value , 30*24*60*60);
			return value;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}




































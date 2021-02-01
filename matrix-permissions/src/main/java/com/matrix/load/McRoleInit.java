package com.matrix.load;


import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.pojo.cache.McRoleCache;
/**
 * @description: 如果缓存取值为空则此处做处理
 * key: xd-McRole-18
 * value: 
				{
				    "ids": "75,77,79,80,83,84,112,113,114,115,116,126,127,128,129,132,133,134,135,136,137,149,161,162,163,164",
				    "mcRoleId": 18,
				    "roleDesc": "hjy-导航栏A",
				    "roleName": "hjy-导航栏A"
				    "cid": 1
				    "type": "leader",
				    "platform" : "133EFB922DF3"
				}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午9:49:23 
 * @version 1.0.0.1
 */
public class McRoleInit extends BaseClass implements ILoadCache<String> {
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcRoleMapper mcRoleMapper; 
	
	public String load(String key, String field) {
		try {
			McRoleCache e = mcRoleMapper.findRoleCache(Long.parseLong(key));
			if(e == null) {
				return "";
			}
			String value = JSONObject.toJSONString(e);
			launch.loadDictCache(DCacheEnum.McRole , null).set(key , value , 30*24*60*60);
			return value;
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "";
	}

}






























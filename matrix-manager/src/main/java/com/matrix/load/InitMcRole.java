package com.matrix.load;

import java.util.List;

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
				}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午9:49:23 
 * @version 1.0.0.1
 */
public class InitMcRole extends BaseClass implements ILoadCache {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	private List<McRoleCache> list;
	@Inject
	private IMcRoleMapper mcRoleMapper; 
	
	
	@Override
	public String load(String key, String field) {
		// 这里偷懒，沿用LoadCacheMcRole.java中的方法。
		try {
			list = mcRoleMapper.findMcRoleDtoList();
			if(list != null && list.size() != 0){
				for(McRoleCache d : list){
					if(d.getMcRoleId().toString().equals(key)) {
						String value = JSONObject.toJSONString(d);
						launch.loadDictCache(DCacheEnum.McRole , null).set(key , value);
						return value;
					}
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return "";
	}

}






























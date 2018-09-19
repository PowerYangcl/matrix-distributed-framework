package com.matrix.dict;

import java.util.List;
import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcRoleMapper;
import com.matrix.pojo.cache.McRoleCache;

/**
 * @descriptions 加载角色相关缓存到数据库
 *
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
 * @date 2017年4月17日 下午8:25:32
 * @version 1.0.1
 */
public class LoadCacheMcRole extends BaseClass implements IBaseCache{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	private List<McRoleCache> list;
	 
	@Inject
	private IMcRoleMapper mcRoleMapper; 
	
	
	@Override
	public void refresh() {
		try {
			list = mcRoleMapper.findMcRoleDtoList();
			if(list != null && list.size() != 0){
				for(McRoleCache d : list){
					launch.loadDictCache(DCacheEnum.McRole , null).set(d.getMcRoleId().toString() , JSONObject.toJSONString(d));
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		System.out.println(this.getClass().getName() + " - 缓存初始化完成!"); 
	}

	@Override
	public void removeAll() {
		try {
			list = mcRoleMapper.findMcRoleDtoList();
			if(list != null && list.size() != 0){
				for(McRoleCache d : list){
					launch.loadDictCache(DCacheEnum.McRole , null).del(d.getMcRoleId().toString()); 
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		System.out.println(this.getClass().getName() + " - 缓存删除完成!"); 
	}
}











































package com.matrix.load;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.dao.IMcUserRoleMapper;
import com.matrix.pojo.cache.McRoleCache;
import com.matrix.pojo.cache.McUserRoleCache;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.McUserInfo;
import com.matrix.pojo.entity.McUserRole;
/**
 * @description: 如果缓存取值为空则此处做处理
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午10:09:18 
 * @version 1.0.0.1
 */
public class McUserRoleInit extends BaseClass implements ILoadCache<String> {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	@Inject
	private IMcUserRoleMapper mcUserRoleMapper;
	
	
	@Override
	public String load(String key, String field) {
		McUserInfo e = mcUserInfoMapper.find(Long.parseLong(key));
		if(e == null) {
			return "";
		}
		return this.reloadUserFunction(e.getId()); 
	}
	
	private String reloadUserFunction(Long userId){
		McUserRoleCache cache = new McUserRoleCache();
		cache.setMcUserId(userId);
		List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(userId);
		if(list != null && list.size() != 0){
			Set<Long> set = new TreeSet<Long>();  
			for(McUserRole r : list){
				String roleJson = launch.loadDictCache(CachePrefix.McRole , "McRoleInit").get(r.getMcRoleId().toString());
				if(StringUtils.isNotBlank(roleJson)){
					McRoleCache role = JSONObject.parseObject(roleJson, McRoleCache.class);
					if(role == null){
						continue;
					}
					if(StringUtils.isNotBlank(role.getIds())){
						String [] arr = role.getIds().split(",");
						for(String s : arr){
							set.add(Long.valueOf(s)); 
						}
					}
				}
			}
			if(set != null && set.size() != 0){
				for(Long id : set){
					String rfJson = launch.loadDictCache(CachePrefix.McSysFunc , "McSysFuncInit").get(id.toString());
					if(StringUtils.isNotBlank(rfJson)){
						McSysFunction rf = JSONObject.parseObject(rfJson, McSysFunction.class);
						if(rf == null){
							continue;
						}
						cache.getMsfList().add(rf); 
					}
				}
			}
			String value = JSONObject.toJSONString(cache);
			launch.loadDictCache(CachePrefix.McUserRole , null).set(userId.toString(), value , 30*24*60*60); 
			return value;
		}else {
			return "";
		}
	}

}



































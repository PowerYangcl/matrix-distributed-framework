package com.matrix.load;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
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

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 设置当前用户下platform对应的pageJson信息
 * 		通常一个user用户会有多个平台的登录权限。
 * 		此处根据用户关联的多个platform码来设置多条pageJson缓存，然后返回对应的pageJson
 * 		recode in v1.6.1.6-multiple-jspweb
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午10:09:18 
 * @version 1.0.0.1
 */
@Slf4j
public class McUserRoleInit extends BaseClass implements ILoadCache<String> {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	@Inject
	private IMcUserRoleMapper mcUserRoleMapper;
	
	/**
	 * key : platform@userInfoId
	 */
	@Override
	public String load(String key, String field) {
		String platform = key.split("@")[0];
		String userInfoId = key.split("@")[1];
		McUserInfo e = mcUserInfoMapper.find(Long.parseLong(userInfoId));
		if(e == null) {
			log.error("McUserRoleInit load() McUserInfo == null userInfoId=" + userInfoId + " Not Found");
			return "";
		}
		return this.reloadUserFunction(e, platform); 
	}
	
	private String reloadUserFunction(McUserInfo e, String targetPlatform) {
		Long userId = e.getId();
		Map<String, List<McRoleCache>> map = this.buildRoleCache(userId);
		
		String[] platforms = e.getPlatform().split(",");
		if(platforms.length != 0) {
			for(String platform : platforms) {
				McUserRoleCache cache = new McUserRoleCache();
				cache.setMcUserId(userId);
				cache.setPlatform(platform);
				
				List<McRoleCache> list = map.get(platform);
				if(CollectionUtils.isEmpty(list)) {
					continue;
				}
				Set<Long> set = new TreeSet<Long>();  // 保存当前platform下McSysFunc的id集合
				for(McRoleCache role : list) {
					if(StringUtils.isNotBlank(role.getIds())){
						String [] arr = role.getIds().split(",");
						for(String s : arr){
							set.add(Long.valueOf(s)); 
						}
					}
				}
				if(set != null && set.size() != 0) {
					String setStr = set.toString();	// 提高查询效率，对列表进行整体缓存初始化，避免单个数据库链接查询产生的开销。
					String key = setStr.substring(1, setStr.length()-1).replace(" ", "");
					long startTime = System.currentTimeMillis();
					launch.loadDictCache(CachePrefix.McSysFuncList , "McSysFuncListInit").get(key);
					long endTime = System.currentTimeMillis();
					log.info("程序运行时间：" + (endTime - startTime) + "ms");
					
					for(Long id : set) {
						String rfJson = launch.loadDictCache(CachePrefix.McSysFunc , "McSysFuncInit").get(id.toString());
						if(StringUtils.isNotBlank(rfJson)){
							McSysFunction rf = JSONObject.parseObject(rfJson, McSysFunction.class);
							if(rf == null){
								log.error("McUserRoleInit reloadUserFunction() McSysFunction == null id=" + id + " Not Found");
								continue;
							}
							cache.getMsfList().add(rf);
						}
					}
				}
				String key = platform + "@" + userId; // 此处会设置多条缓存记录。
				String value = JSONObject.toJSONString(cache);
				launch.loadDictCache(CachePrefix.McUserRole , null).set(key, value , 30*24*60*60); 
			}
			return launch.loadDictCache(CachePrefix.McUserRole , null).get(targetPlatform + "@" + userId);
		}
		return "";
	}
	
	
	private Map<String , List<McRoleCache>> buildRoleCache(Long userId){
		Map<String , List<McRoleCache>> map = new HashMap<String , List<McRoleCache>>();
		List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(userId);
		if(CollectionUtils.isNotEmpty(list)) {
			for(McUserRole r : list){
				String roleJson = launch.loadDictCache(CachePrefix.McRole , "McRoleInit").get(r.getMcRoleId().toString());
				if(StringUtils.isNotBlank(roleJson)){
					McRoleCache role = JSONObject.parseObject(roleJson, McRoleCache.class);
					if(role == null) {
						log.error("McUserRoleInit buildRoleCache() McRoleCache == null mcRoleId=" + r.getMcRoleId() + " Not Found");
						continue;
					}
					String platform = role.getPlatform();
					if(map.containsKey(platform)) {
						map.get(platform).add(role);
					}else {
						List<McRoleCache> list_ = new ArrayList<McRoleCache>();
						list_.add(role);
						map.put(platform, list_);
					}
				}
			}
		}
		return map;
	}

}



































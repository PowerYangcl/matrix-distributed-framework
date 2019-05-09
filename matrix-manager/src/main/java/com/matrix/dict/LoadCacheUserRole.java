package com.matrix.dict;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.IBaseCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
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
 * @descriptions 加载用户权限
 *		注意：此处需要这两个类先加载完成，否则无法取得缓存，会报错。LoadCacheMcRole和LoadCacheSysFunction
 *
 * key: xd-McUserRole-1
 	value: 
				{
				    "mcUserId": 1,
				    "msfList": [
				        {
				            "createTime": 1491383651000,
				            "createUserId": 1,
				            "eleType": "",
				            "eleValue": "",
				            "flag": 1,
				            "funcUrl": "",
				            "id": 75,
				            "mcSellerCompanyId": 1,
				            "name": "惠家有促销系统",
				            "navType": 0,
				            "platform":"133C9CB27E18",
				            "parentId": "1",
				            "remark": "惠家有促销系统2",
				            "seqnum": 1,
				            "styleClass": "",
				            "styleKey": "",
				            "updateTime": 1492933675000,
				            "updateUserId": 2
				        },
				        {
				            "createTime": 1491383733000,
				            "createUserId": 1,
				            "eleType": "",
				            "eleValue": "",
				            "flag": 1,
				            "funcUrl": "",
				            "id": 77,
				            "mcSellerCompanyId": 1,
				            "name": "导航栏A",
				            "navType": 1,
				            "platform":"133C9CB27E18",
				            "parentId": "75",
				            "remark": "导航栏A",
				            "seqnum": 1,
				            "styleClass": "",
				            "styleKey": "",
				            "updateTime": 1491383733000,
				            "updateUserId": 1
				        }
				    ]
				} 	
 * @author Yangcl 
 * @home https://github.com/PowerYangcl
 * @date 2017年4月23日 下午9:46:50
 * @version 1.0.1
 */
public class LoadCacheUserRole extends BaseClass implements IBaseCache {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	 
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	@Inject
	private IMcUserRoleMapper mcUserRoleMapper;
	
	public void refresh() {
		List<McUserInfo> list = mcUserInfoMapper.queryPage(null);
		if(list != null && list.size() != 0){
			for(McUserInfo u : list){
				this.reloadUserFunction(u.getId()); 
			}
		}
		System.out.println(this.getClass().getName() + " - 缓存初始化完成!");
	}
	
	/**
	 * @description: 实例化用户功能缓存   
	 * 
	 * @param userId
	 * @author Yangcl 
	 * @date 2017年5月24日 下午3:05:35 
	 * @version 1.0.0.1
	 */
	private void reloadUserFunction(Long userId){
		McUserRoleCache cache = new McUserRoleCache();
		cache.setMcUserId(userId);
		List<McUserRole> list = mcUserRoleMapper.selectByMcUserId(userId);
		if(list != null && list.size() != 0){
			Set<Long> set = new TreeSet<Long>();  
			for(McUserRole r : list){
				String roleJson = launch.loadDictCache(DCacheEnum.McRole , "InitMcRole").get(r.getMcRoleId().toString());
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
					String rfJson = launch.loadDictCache(DCacheEnum.McSysFunc , "InitMcSysFunc").get(id.toString());
					if(StringUtils.isNotBlank(rfJson)){
						McSysFunction rf = JSONObject.parseObject(rfJson, McSysFunction.class);
						if(rf == null){
							continue;
						}
						cache.getMsfList().add(rf); 
					}
				}
			}
			launch.loadDictCache(DCacheEnum.McUserRole , null).set(userId.toString() , JSONObject.toJSONString(cache) , 30*24*60*60); 
		}
	}

	public void removeAll() {
		List<McUserInfo> list = mcUserInfoMapper.queryPage(null);
		if(list != null && list.size() != 0){
			for(McUserInfo u : list){
				launch.loadDictCache(DCacheEnum.McUserRole , null).del(u.getId().toString());  
			}
		} 
		System.out.println(this.getClass().getName() + " - 缓存删除完成!");
	}

}
































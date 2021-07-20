package com.matrix.load;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IJobGroupMapper;
import com.matrix.pojo.entity.JobGroup;


/**
 * @description: 获取定时任务组的缓存信息
 *		key: xd-SysJobGroup-298357293792834
 *
 *		TODO 补充value!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月1日 下午4:35:04 
 * @version 1.0.0.1
 */
public class SysJobGroupInit extends BaseClass implements ILoadCache<String>{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IJobGroupMapper jobGroupMapper;
	
	public String load(String key, String field) {
		JobGroup s = jobGroupMapper.find(Long.valueOf(key));
		if(s != null) {
			JSONObject result = new JSONObject();
			result.put("id", s.getId().toString());
			result.put("groupName" , s.getGroupName());
			result.put("ip" , s.getIp() );
			result.put("remark" , s.getRemark());
			result.put("updateTime" , s.getUpdateTime());
			result.put("updateUser" , s.getUpdateUserName()); 
			
			String value = result.toJSONString();
			launch.loadDictCache(DCacheEnum.SysJobGroup , null).set(key, value , 24*60*60);
			return value;
		}
		return "";
	}

}

































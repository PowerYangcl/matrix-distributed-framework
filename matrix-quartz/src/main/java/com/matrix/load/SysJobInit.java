package com.matrix.load;

import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IJobInfoMapper;
import com.matrix.pojo.entity.JobInfo;

/**
 * @description: 系统定时任务缓存信息
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2018年9月1日 下午7:05:08 
 * @version 1.0.0.1
 */
public class SysJobInit  extends BaseClass implements ILoadCache<String>{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IJobInfoMapper jobInfoMapper;
	
	public String load(String key, String field) {		// key job_name as uuid
		JSONObject result = new JSONObject();
		JobInfo e = jobInfoMapper.findByName(key);
		if(e != null) {
			result = JSONObject.parseObject(JSONObject.toJSONString(e)); 
			
			String group = launch.loadDictCache(DCacheEnum.SysJobGroup, "SysJobGroupInit").get( String.valueOf(e.getRunGroupId()) );
			if(StringUtils.isNotBlank(group)) {
				JSONObject gr = JSONObject.parseObject(group);
				result.put("runGroupId" , gr.getLong("id"));
				result.put("groupName" , gr.getString("groupName"));
				result.put("ip", gr.getString("ip")); 
			}
			
			String value = result.toJSONString();
			launch.loadDictCache(DCacheEnum.SysJob , null).set(key, value , 24*60*60);
			return value;
		}
		return "";
	}

}































package com.matrix.load;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.CachePrefix;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcOrganizationMapper;
import com.matrix.pojo.entity.McOrganization;

/**
 * @description: 实例化组织机构缓存信息
 * 
 * 	key: 表中的主键ID
 * 	
 * 	launch.loadDictCache(DCacheEnum.McOrganization , "McOrganizationInit").get("");
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年7月16日 下午4:00:36 
 * @version 1.0.0.1
 */
public class McOrganizationInit extends BaseClass implements ILoadCache<String> {
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IMcOrganizationMapper mcOrganizationMapper;
	
	
	public String load(String key, String field) {
		McOrganization org = mcOrganizationMapper.find(Long.valueOf(key));
		JSONObject e = new JSONObject();
		if (org == null) {
			e.put("status", "error");
			e.put("msg", this.getInfo(400010043));    // 400010043=查询组织机构信息失败, 查询结果集为空。
			e.put("cid", "0");
			e.put("name", "未知");
			e.put("platform", "未知");
			return e.toJSONString();
		}
		
		e.put("status", "success");
		e.put("msg", this.getInfo(400010014));    // 400010014=查询成功
		e.put("cid", org.getCid() );
		e.put("name", org.getName() );
		e.put("parentId", org.getParentId() );
		e.put("type", org.getType() );
		e.put("platform", org.getPlatform() );
		e.put("managerId", org.getManagerId() );
		e.put("managerName", org.getManagerName() );
		e.put("storeType", org.getStoreType() );
		e.put("seqnum", org.getSeqnum() );
		e.put("mobile", org.getMobile() );
		e.put("address", org.getAddress() );
		e.put("remark", org.getRemark() );
		
		String value = e.toJSONString();
		launch.loadDictCache(CachePrefix.McOrganization , null).set(key , value , 30*24*60*60);
		return value;
	}

}

























package com.matrix.load;


import java.util.ArrayList;
import java.util.List;

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
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.dto.McSysFunctionDto;
import com.matrix.pojo.entity.McSysFunction;
import com.matrix.pojo.entity.RedisEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * @description: 提高查询效率，对列表进行整体缓存初始化，避免单个数据库链接查询产生的开销。
 * 
 * @author Yangcl
 * @date 2022-12-19 18:26:38
 * @home https://github.com/PowerYangcl
 * @path matrix-permissions / com.matrix.load.McSysFuncListInit.java
 * @version v1.6.1.6-multiple-jspweb
 */
@Slf4j
public class McSysFuncListInit extends BaseClass implements ILoadCache<String> {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcSysFunctionMapper mcSysFunctionMapper; 
	
	
	/**
	 * @description: key：McSysFunc的id集合，英文逗号分隔
	 *
	 * @author Yangcl
	 * @date 2022-12-19 18:26:11
	 * @home https://github.com/PowerYangcl
	 * @version v1.6.1.6-multiple-jspweb
	 */
	public String load(String ids, String field) {
		try {
			McSysFunctionDto dto = new McSysFunctionDto();
			dto.setIds(ids);
			List<McSysFunction> list = mcSysFunctionMapper.findListByIds(dto);
			if(CollectionUtils.isNotEmpty(list)) {
				List<RedisEntity> reList = new ArrayList<RedisEntity>();
				for(McSysFunction e : list) {
					String id = e.getId() + "";
					String rfJson = launch.loadDictCache(CachePrefix.McSysFunc , "").get(id.toString());
					if(StringUtils.isNotBlank(rfJson)) {
						log.info("McSysFuncListInit load() McSysFunction in redis id=" + id);
						continue;
					}
					
					e.setUserCache(null);
					e.buildNullCommon();
					String value = JSONObject.toJSONString(e); 
					RedisEntity re = new RedisEntity();
					re.setKey(id);
					re.setValue(value);
					reList.add(re);
//					launch.loadDictCache(CachePrefix.McSysFunc , null).set(id , value , 30*24*60*60);
				}
				launch.loadDictCache(CachePrefix.McSysFunc , null).batchInsert(reList, 30*24*60*60);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			log.error("McSysFuncListInit load() Exception! " + ex);
			return "error";
		}
		return "success";
	}
	
}




































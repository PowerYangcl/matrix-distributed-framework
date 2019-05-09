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
import com.matrix.dao.IAcIncludeDomainMapper;
import com.matrix.pojo.view.AcIncludeDomainView;

/**
 * @description: 加载 apicenter.ac_include_domain表的字典缓存到数据库，用于标识跨域白名单
 * key: xd-ApiDomain-all
 * value:
			 {
			    "status": "success",
			    "data": [
			        {
			            "domain": "www.baidu.com",
			            "companyName": "百度",
			            "createTime": 1510932551000,
			            "updateUserId": 1,
			            "updateTime": 1510932551000,
			            "id": 1,
			            "updater": "admin"
			        },
			        {
			            "domain": "www.sina.com",
			            "companyName": "sina",
			            "createTime": 1511012845000,
			            "updateUserId": 1,
			            "updateTime": 1511012845000,
			            "id": 3,
			            "updater": "admin"
			        }
			    ]    
			}
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月14日 下午5:26:17 
 * @version 1.0.0
 */
public class LoadCacheAcIncludeDomainList extends BaseClass implements IBaseCache{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IAcIncludeDomainMapper acIncludeDomainMapper;
	
	
	@Override
	public void refresh() {
		List<AcIncludeDomainView> list = acIncludeDomainMapper.queryPageList(null); 
		if(list != null && list.size() > 0) {
			JSONObject cache = new JSONObject();
			cache.put("status", "success");
			cache.put("data", list);
			launch.loadDictCache(DCacheEnum.ApiDomain , null).set("all" , cache.toJSONString());  
			System.out.println(this.getClass().getName() + " - 缓存初始化完成!"); 
		}
	}

	@Override
	public void removeAll() {
		launch.loadDictCache(DCacheEnum.ApiDomain , null).del("all");
		System.out.println(this.getClass().getName() + " - 缓存删除完成!"); 
	}

}


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
import com.matrix.dao.IAcApiProjectMapper;
import com.matrix.pojo.view.AcApiProjectView;

/**
 * @description: 加载 apicenter.ac_api_project表的字典缓存，用于标识API的分类
 * key: xd-ApiProject-all
 * value:
				 {
				    "status": "success",
				    "data": [
				        {
				            "createTime": 1510642039000,
				            "updateUserId": 1,
				            "updateTime": 1510644136000,
				            "id": 1,
				            "target": "测试api组-内部",
				            "updater": "admin"
				        },
				        {
				            "createTime": 1510643604000,
				            "updateUserId": 1,
				            "updateTime": 1510644154000,
				            "id": 2,
				            "target": "open-api",
				            "updater": "admin"
				        },
				        {
				            "createTime": 1510649768000,
				            "updateUserId": 1,
				            "updateTime": 1510650062000,
				            "id": 6,
				            "target": "惠家有app",
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
public class LoadCacheAcApiProjectList extends BaseClass implements IBaseCache{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IAcApiProjectMapper acApiProjectMapper;
	
	
	@Override
	public void refresh() {
		List<AcApiProjectView> list = acApiProjectMapper.findAll();
		if(list != null && list.size() > 0) {
			JSONObject cache = new JSONObject();
			cache.put("status", "success");
			cache.put("data", list);
			launch.loadDictCache(DCacheEnum.ApiProject , null).set("all" , cache.toJSONString());  
			System.out.println(this.getClass().getName() + " - 缓存初始化完成!"); 
		}
	}

	@Override
	public void removeAll() {
		launch.loadDictCache(DCacheEnum.ApiProject , null).del("all");
		System.out.println(this.getClass().getName() + " - 缓存删除完成!"); 
	}

}


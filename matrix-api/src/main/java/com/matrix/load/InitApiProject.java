package com.matrix.load;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IAcApiProjectMapper;
import com.matrix.pojo.view.AcApiProjectView;

/**
 * @description: 加载 apicenter.ac_api_project表的字典缓存
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
				            "serviceUrl":"http://10.23.9.16:80/mip-web-api/",
				            "updater": "admin"
				        },
				        {
				            "createTime": 1510643604000,
				            "updateUserId": 1,
				            "updateTime": 1510644154000,
				            "id": 2,
				            "target": "open-api",
				            "serviceUrl":"http://10.23.9.16:80/mip-web-api/",
				            "updater": "admin"
				        },
				        {
				            "createTime": 1510649768000,
				            "updateUserId": 1,
				            "updateTime": 1510650062000,
				            "id": 6,
				            "target": "惠家有app",
				            "serviceUrl":"http://10.23.9.16:80/mip-web-api/",
				            "updater": "admin"
				        }
				    ]
				}
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月23日 下午14:42:28 
 * @version 1.0.0
 */
public class InitApiProject extends BaseClass implements ILoadCache<String>{
	
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IAcApiProjectMapper acApiProjectMapper;
	
	
	@Override
	public String load(String key, String field) {
		if(!key.equals("all")) {
			JSONObject r = new JSONObject();
			r.put("msg", this.getInfo(300010100, "all"));   // 300010100=该缓存key指向唯一的键值: {0},请勿传入其他key
			return  ""; 					// r.toJSONString(); 	 
		}
		
		List<AcApiProjectView> list = acApiProjectMapper.findAll();
		if(list != null && list.size() > 0) {
			JSONObject cache = new JSONObject();
			cache.put("status", "success");
			cache.put("data", list);
			String value = cache.toJSONString();
			launch.loadDictCache(DCacheEnum.ApiProject , null).set("all" , value);  
			return value ;
		}
		
		return "";
	}

}























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
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.entity.McSysFunction;
/**
 * @description: 如果缓存取值为空则此处做处理
 * key: xd-McSysFunc-75
 * value: 
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
				    "parentId": "1",
				    "remark": "惠家有促销系统2",
				    "seqnum": 1,
				    "styleClass": "",
				    "styleKey": "",
				    "updateTime": 1492933675000,
				    "updateUserId": 2
				}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午9:49:23 
 * @version 1.0.0.1
 */
public class InitMcSysFunc extends BaseClass implements ILoadCache {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcSysFunctionMapper mcSysFunctionMapper; 
	
	private List<McSysFunction> list;
	
	@Override
	public String load(String key, String field) {
		// 这里偷懒，沿用LoadCacheSysFunction.java中的方法。
		try{
			McSysFunction e = new McSysFunction();
//			e.setFlag(1); 
			list = mcSysFunctionMapper.getSysFuncList(e); 
			if(list != null && list.size() != 0){
				for(McSysFunction f : list){
					if(f.getId().toString().equals(key)) {
						String value = JSONObject.toJSONString(f); 
						launch.loadDictCache(DCacheEnum.McSysFunc , null).set(key , value);
						return value;
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}




































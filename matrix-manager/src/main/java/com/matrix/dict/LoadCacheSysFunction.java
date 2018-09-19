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
import com.matrix.dao.IMcSysFunctionMapper;
import com.matrix.pojo.entity.McSysFunction;

/**
 * @descriptions 加载系统功能名称 
 *
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
 * @date 2017年4月23日 下午9:49:49
 * @version 1.0.1
 */
public class LoadCacheSysFunction extends BaseClass implements IBaseCache {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	private List<McSysFunction> list;
	
	@Inject
	private IMcSysFunctionMapper mcSysFunctionMapper; 
	
	public void refresh() {
		try {
			McSysFunction e = new McSysFunction();
			list = mcSysFunctionMapper.getSysFuncList(e); 
			if(list != null && list.size() != 0){
				for(McSysFunction f : list){
					launch.loadDictCache(DCacheEnum.McSysFunc , null).set(f.getId().toString(), JSONObject.toJSONString(f));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.getClass().getName() + " - 缓存初始化完成!");
	}

	public void removeAll() {
		try {
			McSysFunction e = new McSysFunction();
//			e.setFlag(1); 
			list = mcSysFunctionMapper.getSysFuncList(e); 
			if(list != null && list.size() != 0){
				for(McSysFunction f : list){
					launch.loadDictCache(DCacheEnum.McSysFunc , null).del(f.getId().toString());  
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(this.getClass().getName() + "********* 缓存删除完成!"); 
	}

}
































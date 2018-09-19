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
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.view.McUserInfoView;
/**
 * @description: 如果缓存取值为空则此处做处理
 * key: user_name+password（md5加密后的密码）
 * value: 
	 {
	    "id": 1,
	    "userName": "admin",
	    "password": "63a9f0ea7bb98050796b649e85481845",
	    "createTime": 1491362113000, 
	    "platform": "P01",
	    "sex": 1,
	    "picUrl": "",
	    "remark": "admin edit this user",
	    "idcard": "4677",
	    "flag": 2,
	    "email": "root@pm.com",
	    "pageCss": "",
	    "mobile": "13511112221"
	}
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年11月20日 下午10:09:18 
 * @version 1.0.0.1
 */
public class InitUserInfoNp extends BaseClass implements ILoadCache {
	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	
	@Override
	public String load(String key, String field) {
		// 这里偷懒，沿用LoadCacheMcUserInfo.java中的方法。
		List<McUserInfoView> list = mcUserInfoMapper.loadUserInfoList();
		if(list != null && list.size() != 0){
			for(McUserInfoView view :list){
				if( (view.getUserName()+view.getPassword()).equals(key) ) {
					String value = JSONObject.toJSONString(view);
					launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(key , value);
					return value;
				}
			}
		}
		return "";
	}

}




























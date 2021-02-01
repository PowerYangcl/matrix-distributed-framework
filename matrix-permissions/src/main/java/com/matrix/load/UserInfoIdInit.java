package com.matrix.load;

import com.matrix.annotation.Inject;
import com.matrix.base.BaseClass;
import com.matrix.base.interfaces.ILoadCache;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.entity.McUserInfo;

/**
 * @description:根据用户ID加载 
 * 		存UserInfoNp对应key后缀，方便查找和删除；例如：kye:xd-UserInfoId-999|value:userName+","+password
 *
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2019年12月20日 下午6:18:29 
 * @version 1.0.0.1
 */
public class UserInfoIdInit  extends BaseClass implements ILoadCache<String> {

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	
	@Override
	public String load(String key, String field) {
		McUserInfo e = mcUserInfoMapper.find(Long.valueOf(key));
		if(e == null) {
			return "";
		}
		String value = e.getUserName() + "," + e.getPassword();
		launch.loadDictCache(DCacheEnum.UserInfoId , null).set(key , value , 4*60*60);  // 依据user info id，方便操作
		return value;
	}

}

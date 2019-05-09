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
import com.matrix.dao.IMcUserInfoMapper;
import com.matrix.pojo.view.McUserInfoView;

/**
 * @description: 加载用户个人信息
 * 
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
 * 
 * @author Yangcl
 * @home https://github.com/PowerYangcl
 * @date 2017年5月31日 下午2:34:07 
 * @version 1.0.0
 */
public class LoadCacheMcUserInfo  extends BaseClass implements IBaseCache{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	@Inject
	private IMcUserInfoMapper mcUserInfoMapper;
	
	
	@Override
	public void refresh() {
		List<McUserInfoView> list = mcUserInfoMapper.loadUserInfoList();
		if(list != null && list.size() != 0){
			for(McUserInfoView view :list){
				launch.loadDictCache(DCacheEnum.UserInfoNp , null).set(view.getUserName() + "," + view.getPassword() , JSONObject.toJSONString(view) , 30*24*60*60);
			}
		}
		System.out.println(this.getClass().getName() + " - 缓存初始化完成!");
	}

	@Override
	public void removeAll() {
		
	}

}




















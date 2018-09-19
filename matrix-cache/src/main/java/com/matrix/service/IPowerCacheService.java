package com.matrix.service;

import com.alibaba.fastjson.JSONObject;

public interface IPowerCacheService {

	public JSONObject getCacheValue(String prefix , String key , String type); 

}

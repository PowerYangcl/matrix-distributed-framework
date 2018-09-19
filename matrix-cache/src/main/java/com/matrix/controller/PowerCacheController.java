package com.matrix.controller;


import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.service.IPowerCacheService;

@Controller
@RequestMapping("cache")
public class PowerCacheController extends BaseController{
	private static Logger logger=Logger.getLogger(PowerCacheController.class);

	@Autowired
	private IPowerCacheService powerApiService;
	
	/**
	 * @description: 前往缓存查看页面
	 * 
	 * @author Yangcl 
	 * @date 2017年5月25日 下午4:52:23 
	 * @version 1.0.0.1
	 */
	@RequestMapping("page_cache_get_value")  
	public String pageCacheGetValue(HttpSession session){ 
		super.userBehavior(session, logger, "page_cache_get_value", "前往缓存查看页面");
		return "jsp/syssetting/cache/checkCache"; 
	}
	
	/**
	 * @description: 前往缓存重置页面
	 *
	 * @author Yangcl
	 * @date 2017年11月1日 上午10:05:01 
	 * @version 1.0.0
	 */
	@RequestMapping("page_cache_reload")  
	public String pageCacheReload(HttpSession session){ 
		super.userBehavior(session, logger, "page_cache_reload", "前往缓存重置页面");
		return "jsp/syssetting/cache/cacheReload"; 
	}
	

	/**
	 * @description: 查看缓存中的数据状态信息
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存中的key
	 * @param type 缓存类型 ：dict|serv
	 * @author Yangcl 
	 * @date 2017年5月26日 上午11:30:50 
	 * @version 1.0.0.1
	 */
	@RequestMapping(value = "ajax_get_cache_value", produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public JSONObject getCacheValue(String prefix , String key , String type , HttpSession session){
		super.userBehavior(session, logger, "get_cache_value", "查看缓存中的数据状态信息");
		return powerApiService.getCacheValue(prefix , key , type);
	}
}



























package com.matrix.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseController;
import com.matrix.service.IPowerCacheService;

@Controller
@RequestMapping("cache")
public class PowerCacheController extends BaseController {

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
    public String pageCacheGetValue(HttpSession session) {
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
    public String pageCacheReload(HttpSession session) {
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
    @RequestMapping(value = "ajax_get_cache_value", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject getCacheValue(String prefix, String key, String type, HttpSession session) {
        return powerApiService.getCacheValue(prefix, key, type);
    }

    /**
     * @description: 删除缓存中的数据
     *
     * @param prefix 缓存key的前缀
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @author Yangcl
     * @date 2018年11月14日 上午11:14:52
     * @version 1.0.0.1
     */
    @RequestMapping(value = "ajax_delete_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxDeleteCache(String prefix, String key, String type, HttpSession session) {
        return powerApiService.ajaxDeleteCache(prefix, key, type);
    }


    /**
     * @description: 通过关键字，批量删除缓存的数据
     *
     * @param prefix 缓存key 的关键字
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @author Sjh
     * @date 2018/11/21 10:55
     * @version 1.0.0.1
     */
    @RequestMapping(value = "ajax_batch_delete_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBatchDeleteCache(String prefix, String key, String type, HttpSession session) {
        return powerApiService.ajaxBatchDeleteCache(prefix, key, type);
    }


    /**
     * @description: 重新设置一个缓存值
     *
     * @param prefix 缓存key的前缀
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @param jsonStr 缓存的json字符串
     * @author Yangcl
     * @date 2018年11月14日 下午19:42:26
     * @version 1.0.0.1
     */
    @RequestMapping(value = "ajax_reset_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxResetCache(String prefix, String key, String type, String jsonStr, HttpSession session) {
        return powerApiService.ajaxResetCache(prefix, key, type, jsonStr);
    }
    
    /**
     * @description: 重新设置一个缓存值|永久生效
     *
     * @param prefix 缓存key的前缀
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @param jsonStr 缓存的json字符串
     * @author Yangcl
     * @date 2018年11月14日 下午19:42:26
     * @version 1.0.0.1
     */
    @RequestMapping(value = "ajax_reset_cache_forever", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxResetCacheForever(String prefix, String key, String type, String jsonStr, HttpSession session) {
        return powerApiService.ajaxResetCacheForever(prefix, key, type, jsonStr);
    }
}



























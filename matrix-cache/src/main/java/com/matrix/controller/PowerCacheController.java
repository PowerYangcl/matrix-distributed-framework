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
    @RequestMapping("page_cache_system_cache")
    public String pageCacheSystemCache(HttpSession session) {
        return "views/system/cache/system-cache";
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
    @RequestMapping(value = "ajax_btn_get_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBtnGetCache(String prefix, String key, String type, HttpSession session) {
        return powerApiService.ajaxBtnGetCache(prefix, key, type);
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
    @RequestMapping(value = "ajax_btn_delete_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBtnDeleteCache(String prefix, String key, String type, HttpSession session) {
        return powerApiService.ajaxBtnDeleteCache(prefix, key, type);
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
    @RequestMapping(value = "ajax_btn_batch_delete", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBtnBatchDeleteCache(String prefix, String key, String type, HttpSession session) {
        return powerApiService.ajaxBtnBatchDeleteCache(prefix, key, type);
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
    @RequestMapping(value = "ajax_btn_reset_cache", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBtnResetCache(String prefix, String key, String type, String jsonStr, HttpSession session) {
        return powerApiService.ajaxBtnResetCache(prefix, key, type, jsonStr);
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
    @RequestMapping(value = "ajax_btn_reset_cache_forever", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public JSONObject ajaxBtnResetCacheForever(String prefix, String key, String type, String jsonStr, HttpSession session) {
        return powerApiService.ajaxBtnResetCacheForever(prefix, key, type, jsonStr);
    }
}



























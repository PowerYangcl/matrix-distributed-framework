package com.matrix.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.Result;

public interface IPowerCacheService {

    public Result<?> ajaxBtnGetCache(String prefix, String key, String type);

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
    public Result<?> ajaxBtnDeleteCache(String prefix, String key, String type);


    /**
     * @description: 批量删除缓存数据
     *
     * @author Yangcl
     * @date 2021-5-22 14:19:02
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    public Result<?> ajaxBtnBatchDeleteCache(String prefix, String key, String type);

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
    public Result<?> ajaxBtnResetCache(String prefix, String key, String type, String jsonStr);

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
	public Result<?> ajaxBtnResetCacheForever(String prefix, String key, String type, String jsonStr);

    /**
     * @description: 缓存API实例化
     *
     * @param dto
     * @author Yangcl
     * @date 2018年12月22日 上午9:21:15 
     * @version 1.0.0.1
     */
	public Result<?> apiCacheInit(JSONObject dto, HttpSession session);

}
























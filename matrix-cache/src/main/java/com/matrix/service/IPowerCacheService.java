package com.matrix.service;

import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;

public interface IPowerCacheService {

    public JSONObject ajaxBtnGetCache(String prefix, String key, String type);

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
    public JSONObject ajaxBtnDeleteCache(String prefix, String key, String type);


    /**
     *@description:批量删除缓存
     *
     *@param prefix 缓存key 的关键字
     *@param key 缓存中的key
     *@param type 缓存类型 ：dict|serv
     *@author Sjh
     *@date 2018/11/21 10:55
     *@return
     *@version 1.0.1
     */

    public JSONObject ajaxBtnBatchDeleteCache(String prefix, String key, String type);

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
    public JSONObject ajaxBtnResetCache(String prefix, String key, String type, String jsonStr);

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
	public JSONObject ajaxBtnResetCacheForever(String prefix, String key, String type, String jsonStr);

    /**
     * @description: 缓存API实例化
     *
     * @param dto
     * @author Yangcl
     * @date 2018年12月22日 上午9:21:15 
     * @version 1.0.0.1
     */
	public JSONObject apiCacheInit(JSONObject dto, HttpSession session);

}
























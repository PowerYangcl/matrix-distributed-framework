package com.matrix.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.service.IPowerCacheService;

@Service("powerCacheService")
public class PowerCacheServiceImpl extends BaseClass implements IPowerCacheService{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * @description: 查看缓存中的数据状态信息
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存key的后缀
	 * @param type 缓存类型 ：dict|serv
	 * @return
	 * @author Yangcl 
	 * @date 2017年5月26日 下午1:16:56 
	 * @version 1.0.0.1
	 */
	public JSONObject getCacheValue(String prefix , String key  , String type) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , "Init" + prefix ).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , "Init" + prefix ).get(key); 
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
		
		
		if(StringUtils.isNotBlank(value)){
			result.put("status", "success");
			result.put("msg", "查询成功");
			try {
				result.put("data", JSONObject.parseObject(value)); 
			} catch (Exception e) {
				e.printStackTrace();
				result.put("data", value); // 非对象而是一个提示消息，比如：300010100=该缓存key指向唯一{0},请勿传入其他key
			}
		}else{
			result.put("status", "error");
			result.put("msg", "未找到对应的值");
		}
		return result;
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
	public JSONObject ajaxDeleteCache(String prefix, String key, String type) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).get(key); 
			}
			
			if(StringUtils.isNotBlank(value)){
				if(type.equals("dict")){
					DCacheEnum [] arr = DCacheEnum.values();
					launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).del(key);
				}else{
					SCacheEnum [] arr = SCacheEnum.values();
					launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).del(key); 
				}
			}else{
				result.put("status", "error");
				result.put("msg", this.getInfo(300010103));  // 300010103=缓存不存在
				return result;
			}
			
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).get(key); 
			}
			
			if(StringUtils.isBlank(value)) {
				result.put("status", "success");
				result.put("msg", this.getInfo(300010101));  // 300010101=缓存删除成功
				return result;
			}else {
				result.put("status", "error");
				result.put("msg", this.getInfo(300010102));  // 300010102=缓存删除失败
				return result;
			}
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}

    /**
     * @description: 通过关键字，批量删除缓存的数据
     *
     * @param prefix 缓存key的前缀
     * @param key 缓存中的key
     * @param type 缓存类型 ：dict|serv
     * @author Sjh
     * @date 2018/11/21 10:55
     * @version 1.0.0.1
     */
	public JSONObject ajaxBatchDeleteCache(String prefix, String key, String type) {
		JSONObject result = new JSONObject();
//		if(StringUtils.isBlank(key)){
//			result.put("status", "error");
//			result.put("msg", "缓存key不得为空");
//			return result;
//		}
		if(StringUtils.isBlank(prefix)){
			result.put("status", "error");
			result.put("msg", "缓存prefix不得为空");
			return result;
		}

		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).batchDel(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).batchDel(key);
			}
			result.put("status", "success");
			result.put("msg", this.getInfo(300010101));  // 300010101=缓存删除成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
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
	public JSONObject ajaxResetCache(String prefix, String key, String type , String jsonStr) {
		JSONObject result = new JSONObject();
		
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60); 
			}
			
			result.put("status", "success");
			result.put("msg", this.getInfo(300010104));  // 300010104=缓存重置成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
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
	public JSONObject ajaxResetCacheForever(String prefix, String key, String type, String jsonStr) {
		JSONObject result = new JSONObject();
		if(StringUtils.isBlank(key)){
			result.put("status", "error");
			result.put("msg", "缓存key不得为空");
			return result;
		}
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr); 
			}
			
			result.put("status", "success");
			result.put("msg", this.getInfo(300010104));  // 300010104=缓存重置成功
			return result;
		} catch (Exception e) {
			result.put("status", "error");
			result.put("msg", "未找到对应缓存前缀：" + prefix);
			return result;
		}
	}

}







































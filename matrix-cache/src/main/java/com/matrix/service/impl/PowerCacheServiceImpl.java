package com.matrix.service.impl;

import java.lang.reflect.Method;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.cache.CacheLaunch;
import com.matrix.cache.enums.DCacheEnum;
import com.matrix.cache.enums.SCacheEnum;
import com.matrix.cache.inf.IBaseLaunch;
import com.matrix.cache.inf.ICacheFactory;
import com.matrix.service.IPowerCacheService;
import com.matrix.util.ExceptionUtils;

@Service("powerCacheService")
public class PowerCacheServiceImpl extends BaseClass implements IPowerCacheService{

	private IBaseLaunch<ICacheFactory> launch = CacheLaunch.getInstance().Launch();
	
	/**
	 * @description: 查看缓存中的数据状态信息
	 * 
	 * @param prefix 缓存key的前缀
	 * @param key 缓存key的后缀
	 * @param type 缓存类型 ：dict|serv
	 * @author Yangcl 
	 * @date 2017年5月26日 下午1:16:56 
	 * @version 1.0.0.1
	 */
	public Result<?> ajaxBtnGetCache(String prefix , String key  , String type) {
		if(StringUtils.isBlank(key)){			// 300010108=缓存key不得为空
			return Result.ERROR(this.getInfo(300010108), ResultCode.INVALID_ARGUMENT);
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , prefix + "Init" ).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , prefix + "Init" ).get(key); 
			}
		} catch (Exception e) {		// 300010109=未找到对应缓存前缀：{0} 
			return Result.ERROR(this.getInfo(300010109, prefix), ResultCode.OPERATION_FAILED);
		}
		
		if(StringUtils.isNotBlank(value)){
			try {
				// 100010100=数据请求成功!
				return Result.SUCCESS(this.getInfo(100010100), JSONObject.parseObject(value));
			} catch (Exception e) {
				e.printStackTrace(); 		// 非对象而是一个提示消息，比如：中华人民共和国万岁！
				return Result.SUCCESS(this.getInfo(100010100), value);
			}
		}else{		// 300010105=未找到对应的值
			return Result.ERROR(this.getInfo(300010105), ResultCode.NOT_FOUND);
		}
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
	public Result<?> ajaxBtnDeleteCache(String prefix, String key, String type) {
		if(StringUtils.isBlank(key)){			// 300010108=缓存key不得为空
			return Result.ERROR(this.getInfo(300010108), ResultCode.INVALID_ARGUMENT);
		}
		String value = "";
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).del(key);
				value = launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).get(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).del(key); 
				value = launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).get(key); 
			}
			
			if(StringUtils.isBlank(value)) { 
				return Result.SUCCESS(this.getInfo(300010101));	// 300010101=缓存删除成功
			} 
			// 300010102=缓存删除失败
			return Result.ERROR(this.getInfo(300010102), ResultCode.ERROR_DELETE);  
		} catch (Exception e) {
			// 300010109=未找到对应缓存前缀：{0} 
			return Result.ERROR(this.getInfo(300010109, prefix), ResultCode.MISMATCH_ARGUMENT);
		}
	}

    /**
     * @description: 批量删除缓存数据
     *
     * @author Yangcl
     * @date 2021-5-22 14:19:02
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
	public Result<?> ajaxBtnBatchDeleteCache(String prefix, String key, String type) {
		if(StringUtils.isBlank(prefix)){			// 300010110=缓存prefix不得为空
			return Result.ERROR(this.getInfo(300010110), ResultCode.INVALID_ARGUMENT);
		}

		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).batchDeleteByPrefix(key);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).batchDeleteByPrefix(key);
			}
			return Result.SUCCESS(this.getInfo(300010101));	// 300010101=缓存删除成功
		} catch (Exception e) {
			// 300010109=未找到对应缓存前缀：{0} 
			return Result.ERROR(this.getInfo(300010109, prefix), ResultCode.MISMATCH_ARGUMENT);
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
	public Result<?> ajaxBtnResetCache(String prefix, String key, String type , String jsonStr) {
		if(StringUtils.isBlank(key)){			// 300010108=缓存key不得为空
			return Result.ERROR(this.getInfo(300010108), ResultCode.INVALID_ARGUMENT);
		}
		
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr , 30*60); 
			}
			return Result.SUCCESS(this.getInfo(300010104));	// 300010104=缓存重置成功
		} catch (Exception e) {
			// 300010109=未找到对应缓存前缀：{0} 
			return Result.ERROR(this.getInfo(300010109, prefix), ResultCode.MISMATCH_ARGUMENT);
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
	public Result<?> ajaxBtnResetCacheForever(String prefix, String key, String type, String jsonStr) {
		if(StringUtils.isBlank(key)){			// 300010108=缓存key不得为空
			return Result.ERROR(this.getInfo(300010108), ResultCode.INVALID_ARGUMENT);
		}
		
		try {
			if(type.equals("dict")){
				DCacheEnum [] arr = DCacheEnum.values();
				launch.loadDictCache(arr[DCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr);
			}else{
				SCacheEnum [] arr = SCacheEnum.values();
				launch.loadServiceCache(arr[SCacheEnum.valueOf(prefix).ordinal()] , null).set(key , jsonStr); 
			}
			return Result.SUCCESS(this.getInfo(300010104));	// 300010104=缓存重置成功
		} catch (Exception e) {
			// 300010109=未找到对应缓存前缀：{0} 
			return Result.ERROR(this.getInfo(300010109, prefix), ResultCode.MISMATCH_ARGUMENT);
		}
	}
	
    /**
     * @description: 缓存API实例化
     *
     * @param dto.name
     * @param dto.param
     * @author Yangcl
     * @date 2018年12月22日 上午9:21:15 
     * @version 1.0.0.1
     */
	public Result<?> apiCacheInit(JSONObject dto, HttpSession session) {
		try {
			Class<?> clazz = Class.forName(dto.getString("name"));   
			if (clazz != null && clazz.getDeclaredMethods() != null) {
				Method m = clazz.getMethod(dto.getString("func") , JSONObject.class);
				Object re = m.invoke(clazz.newInstance() , dto.getJSONObject("param"));
				return Result.SUCCESS(JSONObject.parseObject(re.toString()));
			}
			return Result.ERROR("类名反射异常", ResultCode.SERVER_EXCEPTION);
		} catch (Exception e) {
			return Result.ERROR("exception in you request", ResultCode.SERVER_EXCEPTION,ExceptionUtils.getExceptionInfo(e));
		}
	}

}


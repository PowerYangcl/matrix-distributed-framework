package com.matrix.rpcimpl;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import com.matrix.base.BaseClass;
import com.matrix.base.Result;
import com.matrix.base.ResultCode;
import com.matrix.base.interfaces.IBaseExecute;
import com.matrix.pojo.dto.PowerCacheDto;
import com.matrix.rpc.IMatrixRouteRpcService;
import com.matrix.system.cache.PowerCache;
import com.matrix.util.ExceptionUtils;

/**
 * @description: 缓存信息路由
 *
 * @author Yangcl
 * @date 2018年11月20日 下午2:33:07 
 * @version 1.0.0.1
 */
public class MatrixRouteRpcServiceImpl extends BaseClass implements IMatrixRouteRpcService {

	/**
	 * @description: 添加一个一级缓存|如果Cache对象不存在则创建一个新的
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:45:35 
	 * @version 1.0.0.1
	 */
	public Result<?> addPowerCache(PowerCacheDto dto) {
		if(StringUtils.isAnyBlank(dto.getCacheName() ,dto.getKey() , dto.getValue())) {
			return Result.ERROR(this.getInfo(108010002), ResultCode.ERROR_PARAM, dto);		// 108010002=重要参数异常
		}
		
		PowerCache.getInstance().compelPut(dto.getCacheName(), dto.getKey(), dto.getValue());
		return Result.SUCCESS(this.getInfo(108010000), dto);
	}

	/**
	 * @description: 更新一级缓存
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:01 
	 * @version 1.0.0.1
	 */
	public Result<?> updatePowerCache(PowerCacheDto dto) {
		if(StringUtils.isAnyBlank(dto.getCacheName() ,dto.getKey() , dto.getValue())) {
			return Result.ERROR(this.getInfo(108010002), ResultCode.ERROR_PARAM, dto);		// 108010002=重要参数异常
		}
		PowerCache.getInstance().reset(dto.getCacheName(), dto.getKey(), dto.getValue());
		return Result.SUCCESS(this.getInfo(108010000));
	}

	/**
	 * @description: 删除一级缓存
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:30 
	 * @version 1.0.0.1
	 */
	public Result<?> removePowerCache(PowerCacheDto dto) {
		if(StringUtils.isAnyBlank(dto.getCacheName() ,dto.getKey())) {
			return Result.ERROR(this.getInfo(108010002), ResultCode.ERROR_PARAM, dto);		// 108010002=重要参数异常
		}
		if(PowerCache.getInstance().remove(dto.getCacheName(), dto.getKey())) {
			return Result.SUCCESS(this.getInfo(108010000));
		}
		
		return Result.ERROR(this.getInfo(108010001), ResultCode.ERROR_DELETE);
	}

	/**
	 * @description: 查找一级缓存|如果没有找到对应的值则返回Null
	 *
	 * @param dto
	 * @author Yangcl
	 * @date 2018年11月20日 下午5:46:45 
	 * @version 1.0.0.1
	 */
	public Result<String> findPowerCache(PowerCacheDto dto) {
		if(StringUtils.isAnyBlank(dto.getCacheName() ,dto.getKey())) {
			return Result.ERROR(this.getInfo(108010002), ResultCode.ERROR_PARAM);		// 108010002=重要参数异常
		}
		String value = (String) PowerCache.getInstance().find(dto.getCacheName() , dto.getKey());
		if(StringUtils.isBlank(value)) {
			return Result.ERROR(this.getInfo(108010003), ResultCode.RESULT_NULL); // 108010003=未找到对应的缓存
		}
		
		return Result.SUCCESS(this.getInfo(108010000) , value);
	}

	/**
	 * @description: 并行触发要执行的节点调用类
	 *
	 * @param dto.key invoke class path
	 * @param dto.value function parameters as json string
	 * @author Yangcl
	 * @date 2019年1月5日 上午9:31:12 
	 * @version 1.0.0.1
	 */
	public Result<String> routeExecute(PowerCacheDto dto) {
		if(StringUtils.isBlank(dto.getKey())) {
			return Result.ERROR(this.getInfo(108010002), ResultCode.ERROR_PARAM);		// 108010002=重要参数异常
		}
		String key = this.getConfig("matrix-core." + dto.getKey());
		if(StringUtils.isBlank(key)) {  // 108010006=系统尚未内置您所指定的命令，请与系统设计者联系
			return Result.ERROR(this.getInfo(108010006 , "routeExecute"), ResultCode.OPERATION_FAILED); 
		}
		
		try {
			Class<?> clazz = ClassUtils.getClass(key);
			if (clazz != null && clazz.getDeclaredMethods() != null){
				IBaseExecute exe = (IBaseExecute) clazz.newInstance();
				return Result.SUCCESS("执行完成" , exe.execute(dto.getValue()));
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			return Result.ERROR("操作失败，服务器异常! " + ExceptionUtils.getExceptionInfo(e) , ResultCode.SERVER_EXCEPTION);
		}
		
		return Result.ERROR("操作失败", ResultCode.OPERATION_FAILED);
	}
	
}































































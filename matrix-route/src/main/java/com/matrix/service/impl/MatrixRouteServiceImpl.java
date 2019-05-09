package com.matrix.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.rpc.service.GenericService;
import com.alibaba.fastjson.JSONObject;
import com.matrix.base.BaseClass;
import com.matrix.dubbo.DubboGeneric;
import com.matrix.pojo.dto.PowerCacheDto;
import com.matrix.service.IMatrixRouteService;
import com.matrix.util.ExceptionUtils;

@Service("matrixRouteService")
public class MatrixRouteServiceImpl extends BaseClass implements IMatrixRouteService{
	

	/**
	 * @description: 找到一个指定的一级缓存
	 * 
	 * @param dto.dubboAddr：10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @return {"msg":"操作成功","code":200,"data":"172.22.134.33","class":"com.matrix.base.RpcResult","status":"success"}
	 * @author Yangcl
	 * @date 2019年1月4日 下午2:40:33 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteFindPowerCache(PowerCacheDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getCacheName() , dto.getKey() , dto.getDubboAddr())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		
		DubboGeneric dubboGeneric = new DubboGeneric();
		try {
			GenericService genericService = dubboGeneric.getDubboRpc(
					false, 
					"dubbo://" + dto.getDubboAddr(), 
					"com.matrix.rpc.IMatrixRouteRpcService", 
					null);
			Object obj = genericService.$invoke("findPowerCache", new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto});
			
			return JSONObject.parseObject(JSONObject.toJSONString(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dubboGeneric.destroy();
		}
		
		result.put("status", "error");
		result.put("msg", this.getInfo(108010004 , "findPowerCache"));  // 108010004=泛化调用异常，方法名：{0}
		return result;
	}
	
	/**
	 * @description: 查找多个服务节点中的缓存信息
	 *
 	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * 
	 * @author Yangcl
	 * @date 2019年1月9日 下午5:53:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteFindAll(PowerCacheDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getCacheName() , dto.getKey() , dto.getDubboAddr())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		
		List<String> list = new ArrayList<String>();
		String[] arr = dto.getDubboAddr().split(",");
		for(String host : arr) {
			DubboGeneric dubboGeneric = new DubboGeneric();
			try {
				GenericService genericService = dubboGeneric.getDubboRpc(
						false, 
						"dubbo://" + host, 
						"com.matrix.rpc.IMatrixRouteRpcService", 
						null);
				Object obj = genericService.$invoke("findPowerCache", new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto});
				JSONObject r = JSONObject.parseObject(JSONObject.toJSONString(obj));
				list.add(host + "|" + r.getString("data")); 
			} catch (Exception e) {
				e.printStackTrace();
				list.add(host + "|" + this.getInfo(108010004 , "findPowerCache"));
			}finally {
				dubboGeneric.destroy();
			}
		}
		result.put("status", "success");
		result.put("msg", this.getInfo(108010005));  // 108010005=批量查询完成
		result.put("data", list);
		return result;
	}
	
	

	/**
	 * @description: 添加一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteAddPowerCache(PowerCacheDto dto) {
		return this.operatePowerCache(dto, "addPowerCache");
	}

	/**
	 * @description: 更新一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteUpdatePowerCache(PowerCacheDto dto) {
		return this.operatePowerCache(dto, "updatePowerCache");
	}
	
	/**
	 * @description: 删除一个或多个一级缓存到指定的节点 
	 *
	 * @param dto.dubboAddr：10.12.52.28:20882,10.12.52.29:20883
	 * @param dto.cacheName
	 * @param dto.key
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月4日 下午4:59:44 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteRemovePowerCache(PowerCacheDto dto) {
		return this.operatePowerCache(dto, "removePowerCache");
	}
	
	/**
	 * @description: 一级缓存的增删改三种操作
	 *
	 * @param dto
	 * @param function
	 * @author Yangcl
	 * @date 2019年1月4日 下午5:31:41 
	 * @version 1.0.0.1
	 */
	private JSONObject operatePowerCache(PowerCacheDto dto , String function) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getDubboAddr() , dto.getCacheName() , dto.getKey())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		
		String exception = "";
		String[] arr = dto.getDubboAddr().split(",");
		for(String host : arr) {
			DubboGeneric dubboGeneric = new DubboGeneric();
			try {
				GenericService genericService = dubboGeneric.getDubboRpc(
						false, 
						"dubbo://" + host,  
						"com.matrix.rpc.IMatrixRouteRpcService", 
						null);
				genericService.$invoke(function, new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto});
			} catch (Exception e) {
				e.printStackTrace();
				exception += host + "@" + JSONObject.toJSONString(dto) + ",";
			}finally {
				dubboGeneric.destroy();
			}
		}
		result.put("status", "success");
		result.put("msg", this.getInfo(108010000));  // 108010000=操作成功
		if(StringUtils.isNotBlank(exception)) {
			result.put("status", "error");
			result.put("msg", exception); 
		}
		return result;
	}

	
	/**
	 * @description: 并行触发要执行的节点调用类
	 *
	 * @param dto.dubboAddr：10.12.52.29:20883,10.12.52.30:20884
	 * @param dto.key invoke class path
	 * @param dto.value function parameters as json string
	 * @author Yangcl
	 * @date 2019年1月5日 上午9:31:12 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteExecute(PowerCacheDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getKey() , dto.getDubboAddr())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		
		List<Object> list  = new ArrayList<Object>();
		String[] arr = dto.getDubboAddr().split(",");
		for(int i = 0 ; i < arr.length ; i ++) {
			PowerCacheDto dto_ = new PowerCacheDto();
			dto_.setDubboAddr(arr[i]);
			dto_.setKey(dto.getKey());
			dto_.setValue(dto.getValue());
			
			DubboGeneric dubboGeneric = new DubboGeneric();
			try {
				GenericService genericService = dubboGeneric.getDubboRpc(
						false, 
						"dubbo://" + dto_.getDubboAddr(), 
						"com.matrix.rpc.IMatrixRouteRpcService", 
						null);
				Object obj = genericService.$invoke("routeExecute", new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto_});
				list.add(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				dubboGeneric.destroy();
			}
		}
		
		result.put("status", "success");
		result.put("data", list);
		return result;
	}

	
	/**
	 * @description: 异步获取当前服务器日志信息
	 *			需要批量设置：matrix-core.guard_logger = com.matrix.security.LoggerGuard
	 *
	 * @param dto.dubboAddr
	 * @param dto.value
	 * 
	 * @param session
	 * @author Yangcl
	 * @date 2019年1月14日 下午4:12:02 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteDubboLogger(PowerCacheDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getDubboAddr(), dto.getValue())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		dto.setKey("guard_logger"); 
		DubboGeneric dubboGeneric = new DubboGeneric();
		try {
			GenericService genericService = dubboGeneric.getDubboRpc(
					false, 
					"dubbo://" + dto.getDubboAddr(), 
					"com.matrix.rpc.IMatrixRouteRpcService", 
					null);
			Object obj = genericService.$invoke("routeExecute", new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto});
			return JSONObject.parseObject(JSONObject.toJSONString(obj)); 
		} catch (Exception e) {
			e.printStackTrace();
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
		}finally {
			dubboGeneric.destroy();
		}
		
		result.put("status", "error");
		result.put("msg", this.getInfo(108010004 , "routeExecute|" + dto.getDubboAddr()));  // 108010004=泛化调用异常，方法名：{0}
		return result;
	}

	
	/**
	 * @description: 服务器文件列表
 	 *			需要批量设置：matrix-core.guard_file = com.matrix.security.FileGuard
	 *
	 * @param dto.dubboAddr
	 * @param dto.value
	 * 
	 * @author Yangcl
	 * @date 2019年1月24日 下午2:26:15 
	 * @version 1.0.0.1
	 */
	public JSONObject ajaxRouteServiceFiles(PowerCacheDto dto) {
		JSONObject result = new JSONObject();
		if(StringUtils.isAnyBlank(dto.getDubboAddr(), dto.getValue())) {
			result.put("status", "error");
			result.put("msg", this.getInfo(108010002));  // 108010002=重要参数异常
			return result;
		}
		dto.setKey("guard_file"); 
		DubboGeneric dubboGeneric = new DubboGeneric();
		try {
			GenericService genericService = dubboGeneric.getDubboRpc(
					false, 
					"dubbo://" + dto.getDubboAddr(), 
					"com.matrix.rpc.IMatrixRouteRpcService", 
					null);
			Object obj = genericService.$invoke("routeExecute", new String[]{"com.matrix.pojo.dto.PowerCacheDto"}, new Object[]{dto});
			return JSONObject.parseObject(JSONObject.toJSONString(obj)); 
		} catch (Exception e) {
			e.printStackTrace();
			result.put("exception", ExceptionUtils.getExceptionInfo(e));
		}finally {
			dubboGeneric.destroy();
		}
		
		result.put("status", "error");
		result.put("msg", this.getInfo(108010004 , "routeExecute|" + dto.getDubboAddr()));  // 108010004=泛化调用异常，方法名：{0}
		return result;
	}

	



}






























































































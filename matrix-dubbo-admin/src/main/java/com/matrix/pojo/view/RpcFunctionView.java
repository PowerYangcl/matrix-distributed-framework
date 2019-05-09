package com.matrix.pojo.view;

import java.util.Map;
import java.util.TreeMap;

/**
 * @description: RPC接口中方法实体描述
 *
 * @author Yangcl
 * @date 2018年9月4日 下午2:16:32 
 * @version 1.0.0.1
 */
public class RpcFunctionView {

	private String showName;   // 展示名称
	private String functionName;							// 方法名
	private String returnType;						// 返回值类型
	private Map<String , String> paramMap = new TreeMap<String , String>();	// 接口参数类型列表 treeMap
	
	private String genericString; // 大概这样：public abstract java.lang.Integer com.scrm.services.aem.rpc.IStaffScoreDetailRpcService.countScoreByWhere(com.scrm.services.aem.dto.StaffScoreDetailDto)
	
	
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	public String getReturnType() {
		return returnType;
	}
	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}
	public Map<String, String> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public String getGenericString() {
		return genericString;
	}
	public void setGenericString(String genericString) {
		this.genericString = genericString;
	}
	
	
}

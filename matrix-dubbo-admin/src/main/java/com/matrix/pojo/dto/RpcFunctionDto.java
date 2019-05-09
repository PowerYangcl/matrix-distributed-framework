package com.matrix.pojo.dto;

public class RpcFunctionDto {

	private String rpcName;		// dubbo project rpc name
	private String function;	    // 方法名
	private String functionName;	    // 方法名
	
	
	public String getRpcName() {
		return rpcName;
	}
	public void setRpcName(String rpcName) {
		this.rpcName = rpcName;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getFunctionName() {
		return functionName;
	}
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
}

package com.matrix.base;

import java.io.Serializable;

/**
 * @description: RPC 调用响应封装
 *
 * @author Yangcl
 * @date 2018年9月20日 下午2:40:45 
 * @version 1.0.0.1
 */
public class RpcResult<T> implements Serializable {

	
	private static final long serialVersionUID = 8212029814402861854L;

	private String status;
	private String msg;
	private Integer code = null;
	private T data;
	
	public RpcResult(String status, String msg, Integer code, T data) {
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}
	
	public static <T> RpcResult<T> SUCCESS() {
		return new RpcResult<T>("success" , "" , RpcResultCode.SUCCESS , null);
	}
	
	public static <T> RpcResult<T> SUCCESS(String msg) { 
		return new RpcResult<T>("success" , msg , RpcResultCode.SUCCESS , null);
	}
	
	public static <T> RpcResult<T> SUCCESS(String msg , T t) { 
		return new RpcResult<T>("success" , msg , RpcResultCode.SUCCESS , t);
	}
	
	/**
	 * @description: Rpc错误消息提示
	 *
	 * @param msg
	 * @param code 必须是RpcResultCode中定义的值
	 * 
	 * @author Yangcl
	 * @date 2018年9月20日 下午3:44:41 
	 * @version 1.0.0.1
	 */
	public static <T> RpcResult<T> ERROR(String msg , Integer code) {
		return new RpcResult<T>("error" , msg , code , null);
	}
	
	/**
	 * @description: Rpc错误消息提示
	 *
	 * @param msg
	 * @param code 必须是RpcResultCode中定义的值
	 * 
	 * @author Yangcl
	 * @date 2018年9月20日 下午3:44:41 
	 * @version 1.0.0.1
	 */
	public static <T> RpcResult<T> ERROR(String msg , Integer code , T t) {
		return new RpcResult<T>("error" , msg , code , t);
	}
	
	
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setResult(T data) {
		this.data = data;
	}
	
}

















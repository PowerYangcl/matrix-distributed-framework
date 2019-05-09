package com.matrix.base;

import java.io.Serializable;

/**
 * @description: RPC 调用响应封装
 * 
 * 	使用原则声明：
 * 		    1、针对查询、分页、导出等等这一类的行为，你的RPC实现类和RPC接口类(interface)返回的RpcResult对象中，泛型需要约束为
 * 			一个具体的Java对象；这个泛型对象将携带调用者所需要的信息，它也是你查询的结果。比如：
 * 						public RpcResult<PageInfo<MemberInfo>> memberInfoPageList(MemberInfoDto dto);
 * 						public RpcResult<MemberInfoView> findMemberInfo(Long id);
 * 
 * 			2、针对添加、修改、删除等等这一类行为，如果没有特别需求，你的RPC实现类和RPC接口类(interface)返回的RpcResult对象中，
 * 			泛型需要约束为一个通配符(约定优于配置，此处为团队内部约定后的结果)。比如：
 * 						public RpcResult<?> addMemberInfo(MemberInfo e);
 * 						public RpcResult<?> editMemberInfo(MemberInfo e);
 * 						public RpcResult<?> deleteMemberInfo(MemberInfo e);
 * 			理论上来讲，这里可以约束为任何对象，因为这一类行为我们需要的只有一个结果：成功或失败，失败的话原因是什么，状态码是多少。
 * 			但也会出现极少数的情况，比如添加成功后，我们需要拿到这条记录的主键ID，此时通常会将实体对象一同返回，比如：
 * 						public RpcResult<MemberInfo> addMemberInfoGotId(MemberInfo e);
 * 			实际业务中还会有更多的情况，此处不再一一列举，请开发者根据实际情况决定。
 * 
 * 
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
	
	/**
	 * @description: Rpc消息成功返回提示|无消息文本提示
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> RpcResult<T> SUCCESS() {
		return new RpcResult<T>("success" , "" , RpcResultCode.SUCCESS , null);
	}
	
	/**
	 * @description: Rpc消息成功返回提示|自定义消息提示文本
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> RpcResult<T> SUCCESS(String msg) { 
		return new RpcResult<T>("success" , msg , RpcResultCode.SUCCESS , null);
	}
	
	/**
	 * @description: Rpc消息成功返回提示|附加泛型对象所携带的描述信息|如：insertEntityGotId(E e);
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> RpcResult<T> SUCCESS(String msg , T t) { 
		return new RpcResult<T>("success" , msg , RpcResultCode.SUCCESS , t);
	}
	
	/**
	 * @description: Rpc错误消息提示|不附加泛型类
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
	 * @description: Rpc错误消息提示|附加泛型对象所携带的描述信息
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


















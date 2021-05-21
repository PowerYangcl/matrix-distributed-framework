package com.matrix.base;

import java.io.Serializable;
import lombok.Data;

/**
 * @description: 调用响应封装。支持SpringMvc、Spring-boot、Dubbo的返回值封装。
 * 
 * 	使用原则声明：
 * 		    1、针对查询、分页、导出等等这一类的行为，你的RPC实现类和RPC接口类(interface)返回的Rpc Result对象中，泛型需要约束为
 * 			一个具体的Java对象；这个泛型对象将携带调用者所需要的信息，它也是你查询的结果。比如：
 * 						public Result<PageInfo<MemberInfo>> memberInfoPageList(MemberInfoDto dto);
 * 						public Result<MemberInfoView> findMemberInfo(Long id);
 * 
 * 			2、针对添加、修改、删除等等这一类行为，如果没有特别需求，你的RPC实现类和RPC接口类(interface)返回的Rpc Result对象中，
 * 			泛型需要约束为一个通配符(约定优于配置，此处为团队内部约定后的结果)。比如：
 * 						public Result<?> addMemberInfo(MemberInfo e);
 * 						public Result<?> editMemberInfo(MemberInfo e);
 * 						public Result<?> deleteMemberInfo(MemberInfo e);
 * 			理论上来讲，这里可以约束为任何对象，因为这一类行为我们需要的只有一个结果：成功或失败，失败的话原因是什么，状态码是多少。
 * 			但也会出现极少数的情况，比如添加成功后，我们需要拿到这条记录的主键ID，此时通常会将实体对象一同返回，比如：
 * 						public Result<MemberInfo> addMemberInfoGotId(MemberInfo e);
 * 			实际业务中还会有更多的情况，此处不再一一列举，请开发者根据实际情况决定。
 * 
 * 
 *
 * @author Yangcl
 * @date 2018年9月20日 下午2:40:45 
 * @version 1.0.0.1
 */
@Data
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 8212029814402861854L;

	private String status;
	private String msg;
	private Integer code = null;
	private T data;
	
	public Result(String status, String msg, Integer code, T data) {
		this.status = status;
		this.msg = msg;
		this.code = code;
		this.data = data;
	}
	
	/**
	 * @description: 消息成功返回提示|无消息文本提示
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> SUCCESS() {
		return new Result<T>("success" , "" , ResultCode.SUCCESS , null);
	}
	
	/**
	 * @description: 消息成功返回提示|自定义消息提示文本
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> SUCCESS(String msg) { 
		return new Result<T>("success" , msg , ResultCode.SUCCESS , null);
	}
	
	/**
	 * @description: 消息成功返回提示|自定义消息提示文本
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> SUCCESS(String msg, Integer code) { 
		return new Result<T>("success" , msg , code , null);
	}
	
	/**
	 * @description: 消息成功返回提示|附加泛型对象所携带的描述信息|如：insertEntityGotId(E e);
	 *
	 * @author Yangcl
	 * @date 2018年9月28日 下午3:14:47 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> SUCCESS(String msg , T t) { 
		return new Result<T>("success" , msg , ResultCode.SUCCESS , t);
	}
	
	public static <T> Result<T> SUCCESS(T t) {
		return new Result<T>("success" , "" , ResultCode.SUCCESS , t);
	}
	
	/**
	 * @description: 错误消息提示|不附加泛型类
	 *
	 * @param msg
	 * @param code 必须是RpcResultCode中定义的值
	 * 
	 * @author Yangcl
	 * @date 2018年9月20日 下午3:44:41 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> ERROR(String msg , Integer code) {
		return new Result<T>("error" , msg , code , null);
	}
	
	/**
	 * @description: 错误消息提示|附加泛型对象所携带的描述信息
	 *
	 * @param msg
	 * @param code 必须是RpcResultCode中定义的值
	 * 
	 * @author Yangcl
	 * @date 2018年9月20日 下午3:44:41 
	 * @version 1.0.0.1
	 */
	public static <T> Result<T> ERROR(String msg , Integer code , T t) {
		return new Result<T>("error" , msg , code , t);
	}
}


















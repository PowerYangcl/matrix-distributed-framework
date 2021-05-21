package com.matrix.base;

/**
 * @description: 错误标识码定义
 *
 * @author Yangcl
 * @date 2018年9月20日 下午3:39:12 
 * @version 1.0.0.1
 */
public class ResultCode {
	
	public static Integer SUCCESS = 200;			// 返回成功
	
	
	public static Integer OPERATION_FAILED = 301; // 操作失败

	
	public static Integer INVALID_ARGUMENT = 400;		// 参数错误
	public static Integer MISSING_ARGUMENT = 400;		// 参数缺失
	public static Integer MISMATCH_ARGUMENT_TYPE = 400;  	// 参数类型不匹配
	public static Integer OUT_OF_RANGE = 400;  	// 参数超出范围
	public static Integer INVALID_ACTION = 400;  	// 无效的action
	
	public static Integer NOT_FOUND = 404;  	// 找不到对象
	public static Integer RESULT_NULL = 404;      // 结果集为空
	
	public static Integer ALREADY_EXISTS = 409;      // 对象已存在
	


	public static Integer SERVER_EXCEPTION = 500;  // 服务器异常
	public static Integer SERVER_UNAVAILABLE = 500;  // 服务不可用，指系统服务间调用

	public static Integer ERROR_UPDATE= 501;  // 更新失败

	public static Integer ERROR_INSERT = 502;  // 数据添加失败
	
	public static Integer ERROR_DELETE = 503;  // 数据删除失败

	public static Integer DUPLICATE = 504;  // 数据重复


}









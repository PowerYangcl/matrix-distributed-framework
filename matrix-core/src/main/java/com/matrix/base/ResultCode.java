package com.matrix.base;

/**
 * @description: 错误标识码定义
 *
 * @author Yangcl
 * @date 2018年9月20日 下午3:39:12 
 * @version 1.0.0.1
 */
public class ResultCode {
	
	public static Integer SUCCESS = 200;			// 返回成功		100020100
	
	
	public static Integer OPERATION_FAILED = 300; // 操作失败	100020101
	
	public static Integer PARAM_VALIDATION_FAILED = 301;		// controller层入参校验错误
	public static Integer INTERNAL_VALIDATION_FAILED = 302; // 100020113=内部校验失败

	
	public static Integer INVALID_ARGUMENT = 400;		// 参数错误		100020102
	public static Integer MISSING_ARGUMENT = 400;		// 参数缺失		100020103
	public static Integer MISMATCH_ARGUMENT_TYPE = 400;  	// 参数类型不匹配		100020104
	public static Integer MISMATCH_ARGUMENT = 400;  	// 参数不匹配		100020110
	public static Integer OUT_OF_RANGE = 400;  	// 参数超出范围		100020105
	public static Integer INVALID_ACTION = 400;  	// 无效的action		100020106
	public static Integer ILLEGAL_ARGUMENT = 400;    // 非法的参数
	
	
	public static Integer NOT_FOUND = 404;  	// 找不到对象			100020107
	public static Integer RESULT_NULL = 404;      // 结果集为空		100020108
	
	public static Integer ALREADY_EXISTS = 409;      // 对象已存在		100020109


	public static Integer SERVER_EXCEPTION = 500;  // 服务器异常
	public static Integer SERVER_UNAVAILABLE = 500;  // 服务不可用，指系统服务间调用

	// 100010104=数据更新成功!
	// 100010105=数据更新失败，服务器异常!
	public static Integer ERROR_UPDATE= 501;  // 更新失败

	// 100010102=数据添加成功!
	// 100010103=数据添加失败，服务器异常!
	public static Integer ERROR_INSERT = 502;  // 数据添加失败
	
	// 100010107=数据删除失败，服务器异常!
	public static Integer ERROR_DELETE = 503;  // 数据删除失败

	public static Integer DUPLICATE = 504;  // 数据重复

}









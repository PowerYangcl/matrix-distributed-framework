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
		
	public static Integer RESULT_NULL = 300;      // 结果集为空
	
	public static Integer OPERATION_FAILED = 301; // 操作失败

	public static Integer ERROR_CID = 400;		// cid 为空

	public static Integer ERROR_PARAM = 401;  // 重要参数异常

	public static Integer ERROR_TID = 400;		// tid 为空  Sjh 0905

	public static Integer SERVER_EXCEPTION = 500;  // 服务器异常

	public static Integer ERROR_UPDATE= 501;  // 更新失败

	public static Integer ERROR_INSERT = 502;  // 数据添加失败
	
	public static Integer ERROR_DELETE = 503;  // 数据删除失败

	public static Integer ERROR_FIND = 504;  // 数据获取失败

	public static Integer DUPLICATE = 505;  // 数据重复

	// TODO: 2018/9/28 状态码从小到大、且唯一！！！保证可读性 ！！！

}

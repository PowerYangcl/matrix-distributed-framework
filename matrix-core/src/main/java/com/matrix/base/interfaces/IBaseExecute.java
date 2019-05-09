package com.matrix.base.interfaces;


/**
 * @description: 路由反射接口
 *
 * @author Yangcl
 * @date 2019年1月5日 下午4:12:54 
 * @version 1.0.0.1
 */
public interface IBaseExecute {

	/**
	 * @description: 反射指定的类，同时返回执行后的结果：json字符串
	 *
	 * @param json 参数，JSON字符串
	 * @return 结果，json字符串
	 * @author Yangcl
	 * @date 2019年1月5日 下午4:14:49 
	 * @version 1.0.0.1
	 */
	public String execute(String json);
}

package com.matrix.base.interfaces;

/**
 * 返回结构参数
 * 
 * @author HJY
 * 
 */
public interface IBaseResult {
	/**
	 * @alias getResultCode
	 * 获取结果编号
	 * @return
	 */
	public int getCode();

	/**
	 * @alias getResultMessage
	 * 获取结果信息
	 * @return
	 */
	public String getMessage();
}

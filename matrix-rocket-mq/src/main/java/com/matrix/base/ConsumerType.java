package com.matrix.base;

/**
 * @description: 标识消费者类型；此枚举类禁止添加其他字段 
 *
 * @author Yangcl
 * @date 2019年5月21日 下午7:12:55 
 * @version 1.0.0.1
 */
public enum ConsumerType {
	Concurrently,		// 非顺序消费
	Orderly						// 顺序消费
}

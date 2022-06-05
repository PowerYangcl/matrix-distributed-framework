package com.matrix.aspectj;

import lombok.Data;

import org.hibernate.validator.constraints.Length;

/**
 * @description: 添加、修改、删除等需要进行幂等限制的接口行为，
 * 		其请求controller层请求累需要继承此类。注意：请结合 @Idempotent 注解一起使用
 * 
 * @author Yangcl
 * @date 2022-5-29 18:14:04
 * @home https://github.com/PowerYangcl
 * @path matrix-web-adapter / com.matrix.aspectj.IdempotentRequest.java
 * @version 1.6.1.0-Idempotent
 */
@Data
public class IdempotentRequest {

	@Length(min = 12, max = 36, message="100020118")	// 100020118=字符串长度不在合理区间内
	private String clientToken;
}

package com.matrix.system;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @description: 扩展 com.fasterxml.jackson.databind.ObjectMapper.java 兼容Long与String的转换
 *			com.matrix.system.MatrixObjectMapper
 *
 * @author Yangcl
 * @date 2018年10月29日 下午6:19:54 
 * @version 1.0.0.1
 */
public class MatrixObjectMapper extends ObjectMapper{

	private static final long serialVersionUID = 182915264605659818L;

	public MatrixObjectMapper() {
		super();
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(BigInteger.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        simpleModule.addSerializer(BigDecimal.class, BigDecimalSerializer.instance);
        registerModule(simpleModule);
	}
	


}

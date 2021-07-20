package com.matrix.system;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @description: BigDecimal 序列化
 *
 * @author Yangcl
 * @date 2019年10月18日 下午12:07:38 
 * @version 1.0.0.1
 */
public class BigDecimalSerializer  extends JsonSerializer<BigDecimal>{
  
    public final static BigDecimalSerializer instance = new BigDecimalSerializer();
    
    public final static DecimalFormat df = new DecimalFormat("0.00");

	/** 
	 * @description: bigdecimal格式化两位小数
	 *
	 * @date 2019年10月18日 下午12:07:38 
	 * @version 1.0.0.1
	 */
	public void serialize(BigDecimal value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		if (Objects.isNull(value)) {
			gen.writeNull();
		} else {
			gen.writeObject(df.format(value));
		}
	}

}

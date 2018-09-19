package com.matrix.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @descriptions 不需要判空的字段 
 * 
 * @date 2016年8月29日下午3:15:30
 * @author Yangcl
 * @version 1.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExculdeNullField {

}

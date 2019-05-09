package com.matrix.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *@description: 自定义注解，标识主键字段需要自动增长
 *
 *@author Sjh
 *@date 2019/3/22 11:30
 *@version 1.0.1
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MongoAutoIncKey {


}
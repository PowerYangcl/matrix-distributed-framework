package com.annotation.demo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.SOURCE)
//javac将源程序编译成class文件，在这个过程中类上的注解是否保留到class文件中
//注解的生命周期的三个阶段:源程序,class文件,字节码
//默认值是在class阶段
//Override SuppressWarning Deprecated:按照编译器的标准来判断这三个阶段
@Target({ElementType.ANNOTATION_TYPE,ElementType.TYPE,ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.LOCAL_VARIABLE,ElementType.METHOD,ElementType.PACKAGE,ElementType.PARAMETER})
//注解添加的目标
public @interface MyAnnotation{
	
	String color() default "red";//属性String
	int[] value() default {1};//属性int[],这是个特殊的属性,如果只有一个value属性需要去设置值,可以不需要设置"value="
	MyEnum enums() default MyEnum.ONE;//属性enum,返回值是MyEnum枚举
	MetaAnnotation annotation() default @MetaAnnotation("red");//注解属性
	//静态常量
	boolean isRunning = false;

}

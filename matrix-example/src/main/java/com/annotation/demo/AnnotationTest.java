package com.annotation.demo;

public class AnnotationTest {

	public static void main(String[] args){
		//UserAnnotation类中使用到了MyAnnotation自定的注解
		UseAnnotation.class.isAnnotationPresent(MyAnnotation.class);
		//获取UserAnnotation中上的注解
		MyAnnotation annotation = (MyAnnotation) UseAnnotation.class.getAnnotation(MyAnnotation.class);
		//打印注解
		System.out.println(annotation);
		System.out.println(annotation.color());
		System.out.println(annotation.enums());
		System.out.println(annotation.value().length);
	}
	
}

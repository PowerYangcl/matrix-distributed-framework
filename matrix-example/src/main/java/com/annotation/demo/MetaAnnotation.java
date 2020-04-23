package com.annotation.demo;

public @interface MetaAnnotation {
	
	String name() default "red";
	String value();

}

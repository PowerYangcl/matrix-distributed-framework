package com.matrix.reflects.annotation;

public @interface MetaAnnotation {
	
	String name() default "red";
	String value();

}

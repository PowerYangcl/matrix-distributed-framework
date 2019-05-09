package com.matrix.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description: Inject标签会在spring容器加载完毕(MatrixDistributedFrameworkListener.java执行完成)后工作。
 * 		故不可以使用在Controller、Service层；在这两层使用会导致BaseClass.java的public void inject(Class<?> clazz)方法出现空指针
 * 		通常，除了这两个Spring会初始化扫描的包不可以用，其他任意地方都可以用他来注入资源，以此来增加灵活性。
 *
 * @author Yangcl
 * @date 2018年9月19日 下午6:22:56 
 * @version 1.0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Inject {
	public String className() default "";
}

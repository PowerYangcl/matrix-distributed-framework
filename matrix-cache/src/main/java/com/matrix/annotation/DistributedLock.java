package com.matrix.annotation;

import java.lang.annotation.*;

/**
 * @description: 基于注解的分布式式锁。
 * 		使用时在方法上加@DistributedLock(value="goods", leaseTime=5)
 * 
 * @author Yangcl
 * @date 2021-7-23 17:44:19
 * @home https://github.com/PowerYangcl
 * @path matrix-cache / com.matrix.annotation.DistributedLock.java
 * @version 1.6.0.4-redisson
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DistributedLock {
    /**
     * 锁的名称
     */
    String value() default "redisson";

    /**
     * 锁的有效时间
     */
    int leaseTime() default 10;
}



package com.matrix.launch;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;


/**
 * @description: Rpc服务提供者全局配置
 * 
 * @author Yangcl
 * @date 2022-4-24 14:41:24
 * @home https://github.com/PowerYangcl
 * @path matrix-dubbo / com.matrix.launch.ProviderConfiguration.java
 * @version 1.6.1.0-platform-init
 */
@Configuration
@EnableDubbo(scanBasePackages = "com.matrix.rpc")
@PropertySource("classpath:/properties/dubbo.properties")
public class ProviderConsumerConfiguration {

}

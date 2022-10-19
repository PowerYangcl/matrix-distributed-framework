package com.matrix;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.Resource;

import com.matrix.util.IoUtil;

import lombok.extern.slf4j.Slf4j;


/**
 * @description: 系统网关入口，配置页面在Leader中，所需配置操作在matrix-gateway-config项目中(增删改查逻辑)
 * 		spring.profiles.active 配置在启动参数中【Debug Configurations】【Arguments】【VM arguments】【-Dspring.profiles.active=dev】
 * 		参考：
 * 					https://spring.io/projects/spring-cloud-gateway/#samples
 * 					
 * 					http://www.yl-blog.com/article/703.html | https://github.com/zhoutaoo/SpringCloud.git
 * 					https://www.cnblogs.com/idea360/p/12632801.html					可供参考，他重写了动态路由服务
 * 					http://www.javashuo.com/article/p-pfhkaakh-qm.html
 * 
 * @author Yangcl
 * @date 2022-7-1 10:25:43
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway / com.matrix.MatrixGatewayApplication.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
@Slf4j
@PropertySources({
    @PropertySource(value = {"classpath:application.yml"}, ignoreResourceNotFound = false),
    @PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"}, ignoreResourceNotFound = false)
})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MatrixGatewayApplication {
	
    public static void main(String[] args) {
    	try {
    		log.info("MatrixGatewayApplication 开始启动");
    		
    		String active = System.getProperty("spring.profiles.active");
    		if(StringUtils.isNotBlank(active)) {	// 	准备初始差异化配置文件
    			Resource[] arr = IoUtil.getInstance().listResources("classpath*:/maven-conf/" + active + "/*.properties");
    			if(arr != null && arr.length != 0) {
    				for(Resource rce : arr) {
    					log.info("初始差异化配置文件：" + rce.getFile().getPath());	//  if(rce.getFilename().equals("log4j.properties")) {}
    					PropertyConfigurator.configure(rce.getFile().getPath());
    				}
    			}
    		}
    		SpringApplication.run(MatrixGatewayApplication.class, args);
    		
    		log.info("MatrixGatewayApplication 启动完成");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}























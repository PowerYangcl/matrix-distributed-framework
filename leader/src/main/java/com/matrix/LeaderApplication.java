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
 * @description: 项目入口
 * 		spring.profiles.active 配置在启动参数中【Debug Configurations】【Arguments】【VM arguments】【-Dspring.profiles.active=dev】
 * 
 * @author Yangcl
 * @date 2021-3-11 20:13:24
 * @home https://github.com/PowerYangcl
 * @path leader / com.matrix.LeaderApplication.java
 * @version 1.0.0.1
 */
@Slf4j
@PropertySources({
    @PropertySource(value = {"classpath:application.yml"}, ignoreResourceNotFound = false),
    @PropertySource(value = {"classpath:application-${spring.profiles.active}.yml"}, ignoreResourceNotFound = false)
})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LeaderApplication {
	
    public static void main(String[] args) {
    	try {
    		log.info("LeaderApplication 开始启动");
    		
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
    		SpringApplication.run(LeaderApplication.class, args);
    		
    		log.info("LeaderApplication 启动完成");
		} catch (Exception e) {
			if(e.getClass().getName().contains("SilentExitException")) {  // spring-boot-devtools jsp页面自动刷新导致
				// TODO https://blog.csdn.net/aqsunkai/article/details/69663159  spring-boot-devtools  是否能解决此异常 2022-03-16
	            System.out.println("Spring is restarting the main thread - See spring-boot-devtools");
	        }else {
	        	e.printStackTrace();
	        }
		}
    }

}























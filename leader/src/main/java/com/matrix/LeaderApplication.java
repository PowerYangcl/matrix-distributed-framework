package com.matrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.matrix.base.BaseLog;


/**
 * @description: 项目入口
 * 
 * @author Yangcl
 * @date 2021-3-11 20:13:24
 * @home https://github.com/PowerYangcl
 * @path leader / com.matrix.LeaderApplication.java
 * @version 1.0.0.1
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class LeaderApplication {
	
    public static void main(String[] args) {
    	try {
    		BaseLog.getInstance().setLogger(null).sysoutInfo("LeaderApplication 开始启动" , LeaderApplication.class);
    		SpringApplication.run(LeaderApplication.class, args);
    		BaseLog.getInstance().setLogger(null).sysoutInfo("LeaderApplication 启动完成" , LeaderApplication.class);
		} catch (Exception e) {
			if(e.getClass().getName().contains("SilentExitException")) {  // spring-boot-devtools jsp页面自动刷新导致
	            System.out.println("Spring is restarting the main thread - See spring-boot-devtools");
	        }else {
	        	e.printStackTrace();
	        }
		}
    }

}























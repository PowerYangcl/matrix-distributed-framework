package com.matrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.matrix.base.BaseLog;


/**
 * @description: 网关入口
 * 		访问地址：http://localhost:8086/matrix-gateway/login.html
 * 
 * 		参考：
 * 					https://spring.io/projects/spring-cloud-gateway/#samples
 * 					
 * 					https://cloud.spring.io/spring-cloud-gateway/reference/html/#_after_route_predicate_factory
 * 					http://www.yl-blog.com/article/703.html | https://github.com/zhoutaoo/SpringCloud.git
 * 					https://www.cnblogs.com/idea360/p/12632801.html
 * 					http://www.javashuo.com/article/p-pfhkaakh-qm.html
 * 
 * @author Yangcl
 * @date 2022-7-1 10:25:43
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway / com.matrix.MatrixGatewayApplication.java
 * @version v-2.1.2
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MatrixGatewayApplication {
	
    public static void main(String[] args) {
    	try {
    		BaseLog.getInstance().setLogger(null).sysoutInfo("MatrixGatewayApplication 开始启动" , MatrixGatewayApplication.class);
    		SpringApplication.run(MatrixGatewayApplication.class, args);
    		BaseLog.getInstance().setLogger(null).sysoutInfo("MatrixGatewayApplication 启动完成" , MatrixGatewayApplication.class);
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























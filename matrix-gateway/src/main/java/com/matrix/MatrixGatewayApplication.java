package com.matrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.matrix.base.BaseLog;


/**
 * @description: 网关入口
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
 * @version 1.6.1.4-spring-cloud-gateway
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MatrixGatewayApplication {
	
    public static void main(String[] args) {
    	try {
    		BaseLog.getInstance().setLogger(null).sysoutInfo("MatrixGatewayApplication 开始启动" , MatrixGatewayApplication.class);
    		SpringApplication.run(MatrixGatewayApplication.class, args);
    		BaseLog.getInstance().setLogger(null).sysoutInfo("MatrixGatewayApplication 启动完成" , MatrixGatewayApplication.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }

}























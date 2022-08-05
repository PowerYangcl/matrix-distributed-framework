package com.matrix.launch;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description: spring cloud gateway 初始化网关配置
 * 
 * @author Yangcl
 * @date 2022-8-5 11:02:17
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway / com.matrix.launch.GatewayLaunch.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
@Configuration
public class GatewayLaunch {
	
	
	/**
	 * @description: 从数据库加载接口对应配置信息
	 * 
	 * @param routeLocatorBuilder
	 * @return RouteLocator
	 * @author Yangcl
	 * @date 2022-8-5 11:03:41
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
		RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_atguigu", r -> r.path("/guoji").uri("http://news.baidu.com/guonei"))
                .build();
        
        return routes.build();
	}
}




























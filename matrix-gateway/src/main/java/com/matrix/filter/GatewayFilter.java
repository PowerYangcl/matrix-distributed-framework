package com.matrix.filter;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

/**
 * @description: Spring cloud 网关过滤器
 * 
 * @author Yangcl
 * @date 2022-8-5 11:07:07
 * @home https://github.com/PowerYangcl
 * @path matrix-gateway / com.matrix.filter.GatewayFilter.java
 * @version 1.6.1.4-spring-cloud-gateway
 */
@Component
public class GatewayFilter implements GlobalFilter, Ordered {


	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		
		
		return chain.filter(exchange);
	}

	
	/**
	 * @description: 加载过滤器顺序，数字越小优先级越高
	 *
	 * @author Yangcl
	 * @date 2022-8-5 11:06:28
	 * @home https://github.com/PowerYangcl
	 * @version 1.6.1.4-spring-cloud-gateway
	 */
	@Override
	public int getOrder() {
		return 0;
	}
}

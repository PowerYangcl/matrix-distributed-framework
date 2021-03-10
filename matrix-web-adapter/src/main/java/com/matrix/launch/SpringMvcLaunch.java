package com.matrix.launch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.matrix.listener.MatrixWebListener;
import com.matrix.mvc.NoCacheMappingJacksonHttpMessageConverter;

/**
 * @description: springMvc.xml注解化
 * 
 * @author Yangcl
 * @date 2021-3-10 15:03:53
 * @home https://github.com/PowerYangcl
 * @path matrix-web-adapter / com.matrix.launch.SpringMvcLaunch.java
 * @version 1.0.0.1
 */
@Configuration
@ComponentScan({"com.matrix.controller"})
public class SpringMvcLaunch {

	/**
	 * @description: ServletContextListener web监听器
	 * 
	 * @author Yangcl
	 * @date 2021-3-10 15:11:17
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	@Bean(name = "mw-listener")
	public MatrixWebListener initWebListener() {
		return new MatrixWebListener();
	}
	
	/**
	 * @description: 视图解析器
	 * 
	 * @author Yangcl
	 * @date 2021-3-10 15:11:09
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
	@Bean(name="viewResolver")
	public InternalResourceViewResolver rvr() {
		InternalResourceViewResolver irvr = new InternalResourceViewResolver();
		irvr.setPrefix("/");
		irvr.setSuffix(".jsp");
		return irvr;
	}
	
	/**
	 * @description: Spring mvc 返回json格式 
	 * 
	 * @author Yangcl
	 * @date 2021-3-10 15:17:55
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
//	@Bean(name="mappingJacksonHttpMessageConverter")
//	public MappingJackson2HttpMessageConverter mjhmc() {
//		MappingJackson2HttpMessageConverter mjhmc = new MappingJackson2HttpMessageConverter();
//		List<MediaType> list = new ArrayList<MediaType>();
//		list.add(new MediaType("text/html;charset=UTF-8"));
//		mjhmc.setSupportedMediaTypes(list);
//		return mjhmc;
//	}
	
//	@Bean(name="noCachemappingJacksonHttpMessageConverter")
//	public NoCacheMappingJacksonHttpMessageConverter ncmjhmc() {
//		return new NoCacheMappingJacksonHttpMessageConverter();
//	}
	
//	@Bean
//	public RequestMappingHandlerAdapter rmha() {
//		RequestMappingHandlerAdapter rmha = new RequestMappingHandlerAdapter();
//		return rmha;
//	}
}






























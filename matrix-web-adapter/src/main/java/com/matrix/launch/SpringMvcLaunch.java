package com.matrix.launch;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.matrix.listener.MatrixWebListener;
import com.matrix.system.UrlInterceptor;

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
public class SpringMvcLaunch implements WebMvcConfigurer{

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
	
    @Bean
    public IntrospectorCleanupListener icl() {
    	return new IntrospectorCleanupListener();
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
     * @description: 默认的欢迎页面设置
     *
     * @author Yangcl
     * @date 2021-3-12 14:34:21
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry ){
        registry.addViewController( "/" ).setViewName( "forward:/login.html" );
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE );
    }
    
    /**
     * @description: 注册拦截器，添加拦截路径和排除拦截路径
     *		 excludePathPatterns("login.do" , "logout.do" , "validate_code.do").order(0);//// 添加排除拦截路径 执行顺序
     *
     * @author Yangcl
     * @date 2021-3-12 15:11:41
     * @home https://github.com/PowerYangcl
     * @version 1.0.0.1
     */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UrlInterceptor()).addPathPatterns("/**");  //  /** 的意思是所有文件夹及里面的子文件夹；/* 是所有文件夹，不含子文件夹|/是web项目的根目录
		// 继续注册其他的拦截器：registry.addInterceptor(new Interceptor2()).addPathPatterns("/**"); 
	}
	
	/**
	 * @description: 设置匹配.do后缀的请求
	 * 		srb.addUrlMappings("*.jsp");会导致bug
	 * 
	 * @param dispatcherServlet
	 * @author Yangcl
	 * @date 2021-3-12 10:37:39
	 * @home https://github.com/PowerYangcl
	 * @version 1.0.0.1
	 */
    @Bean
    public ServletRegistrationBean<DispatcherServlet> servletRegistrationBean(DispatcherServlet dispatcherServlet) {
        ServletRegistrationBean<DispatcherServlet> srb = new ServletRegistrationBean<>(dispatcherServlet);
        srb.addUrlMappings("*.do");
        srb.addUrlMappings("*.html");
        srb.addUrlMappings("*.css");
        srb.addUrlMappings("*.js");
        srb.addUrlMappings("*.woff");
        srb.addUrlMappings("*.ico");
        srb.addUrlMappings("*.png");
        srb.addUrlMappings("*.gif");
        srb.addUrlMappings("*.jpeg");
        srb.addUrlMappings("*.jpg");
        srb.addUrlMappings("*.inc");
        return srb;
    }
    
}






























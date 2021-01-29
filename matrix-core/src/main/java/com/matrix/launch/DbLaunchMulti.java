package com.matrix.launch;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

/**
 * @description: 多数据源配置
 * 
 * @author Yangcl
 * @date 2021-1-25 20:25:27
 * @home https://github.com/PowerYangcl
 * @path matrix-core/com.matrix.launch.DbLaunchMulti.java
 * @version 1.0.0.1
 */
@Configuration
@Profile("multi-datasource")
public class DbLaunchMulti {
    @Primary
    @Bean(initMethod = "init")
    @ConfigurationProperties("spring.datasource.druid.one")
    public DataSource dataSourceOne(){
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(initMethod = "init")
    @ConfigurationProperties("spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }
}

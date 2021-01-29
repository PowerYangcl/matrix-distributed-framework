package com.matrix.launch.service;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @description: matrix核心库数据源
 * 
 * @author Yangcl
 * @date 2021-1-26 20:30:51
 * @home https://github.com/PowerYangcl
 * @path api-matrix-web / com.matrix.launch.DataSourceMatrixConfig.java
 * @version 1.0.0.1
 */
@Configuration
@MapperScan(basePackages = "com.matrix.dao", sqlSessionTemplateRef = "matrixSqlSessionTemplate")
public class DataSourceMatrixConfig {

    @Bean(name = "matrixDatasource")
    @ConfigurationProperties(prefix = "spring.datasource.matrix")
    public DruidDataSource datasource() {
    	System.err.println("开始初始化matrix核心库数据源链接。。。。"); 
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name="matrixSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("matrixDatasource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*.xml"));
        bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis-config.xml"));
        return bean.getObject();
    }

    @Bean(name="matrixSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("matrixSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
    
    @Bean(name="matrixTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("matrixDatasource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}






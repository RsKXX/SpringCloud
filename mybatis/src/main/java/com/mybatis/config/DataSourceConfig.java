package com.mybatis.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean
    @Primary
    public DataSource dataSource(@Value("${spring.datasource.dynamic.datasource.first.username}") String username,
                                 @Value("${spring.datasource.dynamic.datasource.first.password}") String password,
                                 @Value("${spring.datasource.dynamic.datasource.first.url}") String url,
                                 @Value("${spring.datasource.dynamic.datasource.first.driver-class-name}") String driverClassName) {
        return createDataSource(username, password, url, driverClassName);
    }

    @Bean
    public DataSource dataSource2(@Value("${spring.datasource.dynamic.datasource.second.username}") String username,
                                  @Value("${spring.datasource.dynamic.datasource.second.password}") String password,
                                  @Value("${spring.datasource.dynamic.datasource.second.url}") String url,
                                  @Value("${spring.datasource.dynamic.datasource.second.driver-class-name}") String driverClassName) {
        return createDataSource(username, password, url, driverClassName);
    }

    private DataSource createDataSource(String username, String password, String url, String driverClassName) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl(url);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}

package com.mybatis.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
@Configuration
@ConditionalOnClass(value = {PaginationInterceptor.class})
@MapperScan("com.mybatis.mapper")
//@MapperScan("com.mybatis.mapper.db2")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        // paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        // paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }

    @Configuration
    @MapperScan(basePackages = "com.mybatis.mapper.db1", sqlSessionTemplateRef = "sqlSessionTemplate")
    public class Db1 {
        @Bean
        @Primary
        public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
            //mybatis应该使用  SqlSessionFactoryBean
            //SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

            //mybatis-plus应该使用  MybatisSqlSessionFactoryBean  否则无法使用baseMapper
            MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(dataSource);
            sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath:com/mybatis/mapper/db1/*.xml"));
            //添加分页功能================================================================================================
//            sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});
            return sqlSessionFactory.getObject();
        }

        @Bean
        @Primary
        public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory){
            return new SqlSessionTemplate(sqlSessionFactory);
        }

        @Bean
        @Primary
        public DataSourceTransactionManager dataSourceTransactionManager(@Qualifier("dataSource") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }

    }


    @Configuration
    @MapperScan(basePackages = "com.mybatis.mapper.db2", sqlSessionTemplateRef = "sqlSessionTemplate2")
    public class Db2 {

        @Bean
        public SqlSessionFactory sqlSessionFactory2(@Qualifier("dataSource2") DataSource dataSource) throws Exception {
            //mybatis应该使用  SqlSessionFactoryBean
            //SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

            //mybatis-plus应该使用  MybatisSqlSessionFactoryBean  否则无法使用baseMapper
            MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
            sqlSessionFactory.setDataSource(dataSource);
            sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                    .getResources("classpath:com/mybatis/mapper/db2/*.xml"));
            //添加分页功能================================================================================================
//            sqlSessionFactory.setPlugins(new Interceptor[]{paginationInterceptor()});
            return sqlSessionFactory.getObject();
        }
        @Bean
        public SqlSessionTemplate sqlSessionTemplate2(@Qualifier("sqlSessionFactory2") SqlSessionFactory sqlSessionFactory) throws Exception {
            return new SqlSessionTemplate(sqlSessionFactory);
        }

        @Bean
        public DataSourceTransactionManager dataSourceTransactionManager2(@Qualifier("dataSource2") DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }


}

package com.gdupt.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XHP
 * @date 2022/11/20
 */
@Configuration
@MapperScan(basePackages = {"com.gdupt.mapper"})
@Slf4j
public class MybatisPlusConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * mybatisplus物理分页插件(limit)
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        return paginationInterceptor;
    }

    /**
     * 拓展mybatisPlus 支持批量插入
     * @return
     */
    @Bean
    public ExpandSqlInjector expandSqlInjector() {
        return new ExpandSqlInjector();
    }


    @Bean
    public DruidDataSource druidDataSource() {
        //
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        log.info("Datasource创建完成 ...");
        log.info(druidDataSource.toString());

        return druidDataSource;
    }

}

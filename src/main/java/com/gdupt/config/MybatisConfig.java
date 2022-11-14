package com.gdupt.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * mybatis配置类
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = {"com.gdupt.mapper"})
@ConfigurationProperties(prefix = "spring.datasource")
@Setter
@Slf4j
@WebServlet(urlPatterns = "/druid/*",
        initParams={
                @WebInitParam(name="allow",value="192.168.1.20,127.0.0.1"),// IP白名单 (没有配置或者为空，则允许所有访问)
                @WebInitParam(name="deny",value="192.168.16.111"),// IP黑名单 (存在共同时，deny优先于allow)
                @WebInitParam(name="loginUsername",value="admin"),// 用户名
                @WebInitParam(name="loginPassword",value="admin"),// 密码
                @WebInitParam(name="resetEnable",value="false")// 禁用HTML页面上的“Reset All”功能
        })
@ServletComponentScan
public class MybatisConfig extends StatViewServlet {

    private String url;

    private String username;

    private String password;

    /**
     * druid数据源
     *
     * @return {@code DruidDataSource}
     */

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

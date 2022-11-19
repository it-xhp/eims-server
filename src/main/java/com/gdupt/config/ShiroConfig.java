package com.gdupt.config;

import com.gdupt.fillter.AuthcFilter;
import com.gdupt.fillter.CommonFilter;
import com.gdupt.shiro.JWTDefaultSubjectFactory;
import com.gdupt.shiro.UserRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * shiro配置类
 */
@Configuration
public class ShiroConfig {



    /**
     * 告诉shiro不创建内置的session
     * @return
     */
    @Bean
    public SubjectFactory subjectFactory(){
        return new JWTDefaultSubjectFactory();
    }

    /**
     * 创建自定义UserRealm
     * @return
     */
    @Bean(name = "userRealm")
    public UserRealm userRealm(){
        return new UserRealm();
    }



    /**
     * 创建 SecurityManager 对象
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        return securityManager;
    }

    /**
     * Filter工厂，设置对应的过滤条件和跳转条件
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        LinkedHashMap<String, String> filterRuleMap = new LinkedHashMap<>();
        // 注意：这里配置的 /login 是指到 @RequestMapping(value="/login")中的 /login
        //shiroFilterFactoryBean.setLoginUrl("/login");
        Map<String, Filter> filters = new HashMap<>();
        Map<String, String> filterChainMap = new HashMap<>();
        filters.put("commonFilter",new CommonFilter());
        filters.put("authcFilter",new AuthcFilter());
        filterRuleMap.put("/**","commonFilter");
        filterRuleMap.put("/**","authcFilter");
        //filterChainMap.put("/login", "anon");
        /**
         * anno 无需认证就可以访问
         * anthc 必须认证才能访问
         * user  必须拥有   记住我  才能访问
         * perms 拥有对某个资源的权限才能访问
         * roles 拥有某个角色权限才能访问
         */
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterRuleMap);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }
}

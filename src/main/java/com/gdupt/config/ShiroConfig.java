package com.gdupt.config;

import com.gdupt.fillter.AuthcFilter;
import com.gdupt.fillter.CommonFilter;
import com.gdupt.shiro.JWTDefaultSubjectFactory;
import com.gdupt.shiro.UserRealm;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
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

    @Bean
    public SessionManager sessionManager() {
        DefaultSessionManager shiroSessionManager = new DefaultSessionManager();
        // 关闭session校验轮询
        shiroSessionManager.setSessionValidationSchedulerEnabled(false);
        return shiroSessionManager;
    }


    @Bean
    public DefaultSubjectDAO subjectDAO(DefaultSessionStorageEvaluator defaultSessionStorageEvaluator) {
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }


    @Bean
    public DefaultSessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        return defaultSessionStorageEvaluator;
    }




        /**
         * 创建自定义UserRealm
         * @return
         */
    @Bean
    public UserRealm userRealm(){
        return new UserRealm();
    }



    /**
     * 创建 SecurityManager 对象
     * @return
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm,SubjectFactory subjectFactory,SessionManager sessionManager,DefaultSubjectDAO subjectDAO){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setSubjectDAO(subjectDAO);
        securityManager.setRealm(userRealm);
        securityManager.setSubjectFactory(subjectFactory);
        securityManager.setSessionManager(sessionManager);
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
        Map<String, Filter> filters = new HashMap<>();
        Map<String, String> filterChainMap = new HashMap<>();
        filters.put("commonFilter",new CommonFilter());
        filters.put("authcFilter",new AuthcFilter());
        filterChainMap.put("/login", "anon");
        filterChainMap.put("/uploads/**", "anon");
        filterChainMap.put("/**", "authcFilter");
        /**
         * anno 无需认证就可以访问
         * anthc 必须认证才能访问
         * user  必须拥有   记住我  才能访问
         * perms 拥有对某个资源的权限才能访问
         * roles 拥有某个角色权限才能访问
         */
        shiroFilterFactoryBean.setFilters(filters);
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置授权
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}

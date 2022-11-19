package com.gdupt.config;

import com.gdupt.interceptor.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private RequestInterceptor requestInterceptor;


    @Value("${server.root-path}")
    String rootPath;

    @Value("${server.base-file-path}")
    private String baseFilePath;



    /**
     * 配置静态资源的，比如html，js，css，等等
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:" + rootPath + baseFilePath );
    }

    /**
     * 注册拦截器，我们自己写好的拦截器需要通过这里添加注册才能生效
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addPathPatterns("/**") 表示拦截所有的请求，
        //excludePathPatterns("/login", "/register") 表示除了登陆与注册之外，因为登陆注册不需要登陆也可以访问
        //registry.addInterceptor(requestInterceptor)
        //        .addPathPatterns("/**")
        //        .excludePathPatterns("/login","/login/getLoginInfo","/register","/css/**","/image/**","/lib/**","/script/**","/uploads/**","/cnc/**","/index/login","/file/uploadImagesAndMedia");
    }

    /**
     * 就是注册的过程，注册Cors协议的内容。
     * 如： Cors协议支持哪些请求URL，支持哪些请求类型，请求时处理的超时时长是什么等。
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                // 所有的当前站点的请求地址，都支持跨域访问
                .addMapping("/**")
                // 当前站点支持的跨域请求类型
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                // 是否支持跨域用户凭证
                .allowCredentials(true)
                // 所有的外部域都可跨域访问。 如果是localhost则很难配置，因为在跨域请求的时候，外部域的解析可能是localhost、127.0.0.1、主机名
                .allowedOriginPatterns("*");
    }
}

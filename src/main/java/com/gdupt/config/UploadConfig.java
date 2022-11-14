package com.gdupt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author xuhuaping
 * @date 2022/9/13
 */
public class UploadConfig {

    @Bean(name = "commonsMultipartResolver")
    public CommonsMultipartResolver fileCommonsMultipartResolver(){
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //请求的编码格式，必须和jSP的pageEncoding属性一致，以便正确读取表单的内容，默认为ISO-8859-1
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        //上传文件大小上限，单位为字节(10485760=10M)
        commonsMultipartResolver.setMaxUploadSize(10485760L);
        commonsMultipartResolver.setMaxInMemorySize(40960);
        return commonsMultipartResolver;
    }
}

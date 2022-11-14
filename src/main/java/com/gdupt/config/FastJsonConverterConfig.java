package com.gdupt.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

import java.util.Arrays;

/**
 * @author xuhuaping
 * @date 2022/9/13
 * fastjson配置
 */
public class FastJsonConverterConfig {

    @Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                // 输出Map中的null值
                SerializerFeature.WriteMapNullValue,
                // List为null时，转换为空List
                SerializerFeature.WriteNullListAsEmpty,
                // String为null时，转换为空字符串
                SerializerFeature.WriteNullStringAsEmpty,
                // 将时间转换为默认格式字符串
                SerializerFeature.WriteDateUseDateFormat,
                // 禁止循环引用
                SerializerFeature.DisableCircularReferenceDetect
        );

        //1.Long型到前端会丢失精度，故序列化成String
        //2.启用全局配置，会影响分页参数 totalCount和currentPage（都是long）
        //3.使用局部配置 @JSONField(serializeUsing = ToStringSerializer.class)
        //SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        //serializeConfig.put(Long.class, ToStringSerializer.instance);
        //serializeConfig.put(Long.TYPE, ToStringSerializer.instance);
        //fastJsonConfig.setSerializeConfig(serializeConfig);

        fastConverter.setFastJsonConfig(fastJsonConfig);

        // 全局指定了日期格式
        // 启用全局配置后无法使用@JSONField注解指定时间格式
        // fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");

        // 该设置目的，为了兼容jackson
        fastConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
        return new HttpMessageConverters(fastConverter);
    }
}

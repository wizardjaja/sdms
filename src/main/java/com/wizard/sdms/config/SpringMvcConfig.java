package com.wizard.sdms.config;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
/**
 * JSON结果解析器
 * @author wizard
 *
 */

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
public class SpringMvcConfig {
	@Bean  
    public HttpMessageConverters fastJsonHttpMessageConverters() {  
       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();  
       FastJsonConfig fastJsonConfig = new FastJsonConfig();  
       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);  
       fastConverter.setFastJsonConfig(fastJsonConfig);  
       HttpMessageConverter<?> converter = fastConverter;  
       return new HttpMessageConverters(converter);  
    }  
}

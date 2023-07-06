package com.pandama.top.feign;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.pandama.top.feign.intercepter.FeignRequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.HttpMessageConverterCustomizer;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.Arrays;
import java.util.Collections;

/**
 * @description: feign客户端配置
 * @author: 王强
 * @dateTime: 2022-10-24 11:59:28
 */
@Configuration
public class FeignConfiguration {

    /**
     * @description: 使用fastjson处理feign的编码
     * @author: 白剑民
     * @date: 2023-06-09 20:40:22
     * @return: feign.codec.Encoder
     * @version: 1.0
     */
    @Bean
    public Encoder feignEncoder() {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.ALL));
        FastJsonConfig config = converter.getFastJsonConfig();
        // 输出结果不带双引号
        config.setSerializerFeatures(SerializerFeature.QuoteFieldNames);
        // 设置HttpMessageConverters为false不再初始化加载默认的转换器以提升转码链速度
        // 如有额外转码需求，请在@FeignClient中指定configuration属性并自定义编解码器
        HttpMessageConverters converters =
                new HttpMessageConverters(false, Collections.singleton(converter));
        return new SpringEncoder(() -> converters);
    }

    /**
     * @description: 使用fastjson处理feign的解码
     * @author: 白剑民
     * @date: 2023-06-09 20:40:22
     * @return: feign.codec.Encoder
     * @version: 1.0
     */
    @Bean
    public Decoder feignDecoder(ObjectProvider<HttpMessageConverterCustomizer> customizers) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.ALL));
        FastJsonConfig config = converter.getFastJsonConfig();
        // 允许不带引号的字段名
        config.setFeatures(Feature.AllowUnQuotedFieldNames);
        // 设置HttpMessageConverters为false不再初始化加载默认的转换器以提升转码链速度
        // 如有额外转码需求，请在@FeignClient中指定configuration属性并自定义编解码器
        HttpMessageConverters converters =
                new HttpMessageConverters(false, Collections.singleton(converter));
        return new ResponseEntityDecoder(new SpringDecoder(() -> converters, customizers));
    }

    /**
     * @description: 注册feign请求拦截器
     * @author: 王强
     * @date: 2023-06-16 13:27:28
     * @return: RequestInterceptor
     * @version: 1.0
     */
    @Bean
    public FeignRequestInterceptor getFeignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}

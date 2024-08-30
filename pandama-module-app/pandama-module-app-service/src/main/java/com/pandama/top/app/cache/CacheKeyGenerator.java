package com.pandama.top.app.cache;

import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.text.MessageFormat;

/**
 * 缓存key生成器
 *
 * @author 王强
 * @date 2024-08-24 21:18:22
 */
@Component
public class CacheKeyGenerator extends SimpleKeyGenerator {

    @Override
    @SuppressWarnings("all")
    public Object generate(Object target, Method method, Object... params) {
        Object generate = super.generate(target, method, params);
        return MessageFormat.format("{0}{1}{2}", method.toGenericString(), generate);
    }

}

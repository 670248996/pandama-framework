package com.pandama.top.core.interfaces;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pandama.top.core.enums.SensitiveEnum;
import com.pandama.top.core.serializers.SensitiveSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 敏感信息包装
 *
 * @author 王强
 * @date 2024-08-21 10:10:46
 */
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveSerialize.class)
public @interface SensitiveWrapped {

    /**
     * 脱敏类型
     */
    SensitiveEnum value();
}

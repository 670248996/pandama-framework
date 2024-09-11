package com.pandama.top.starter.web.serializers;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 本地日期时间序列化器
 *
 * @author 王强
 * @date 2024-09-09 21:33:07
 */
public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {
    @Override
    public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (localDateTime != null) {
            jsonGenerator.writeString(LocalDateTimeUtil.formatNormal(localDateTime));
        }
    }
}
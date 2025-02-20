package com.pandama.top.starter.web.serializers;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 本地日期时间解串器
 *
 * @author 王强
 * @date 2024-09-09 21:33:04
 */
public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String valueAsString = jsonParser.getValueAsString();
        return LocalDateTimeUtil.parse(valueAsString, "yyyy-MM-dd HH:mm:ss");
    }
}
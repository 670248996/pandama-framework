package com.pandama.top.starter.web.serializers;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Date;

/**
 * 日期时间序列化器
 *
 * @author 王强
 * @date 2024-09-09 21:33:07
 */
public class DateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date date, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        if (date != null) {
            jsonGenerator.writeString(DateUtil.formatDateTime(date));
        }
    }
}
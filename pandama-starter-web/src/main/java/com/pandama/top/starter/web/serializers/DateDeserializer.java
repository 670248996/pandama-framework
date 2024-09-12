package com.pandama.top.starter.web.serializers;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Date;

/**
 * 日期时间解串器
 *
 * @author 王强
 * @date 2024-09-09 21:33:04
 */
public class DateDeserializer extends JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String valueAsString = jsonParser.getValueAsString();
        return DateUtil.parseDateTime(valueAsString);
    }
}
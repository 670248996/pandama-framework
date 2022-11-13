package com.pandama.top.global.response;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @description: 枚举值反序列化
 * @author: 白剑民
 * @date : 2022/7/29 10:27
 */
public class StatusEnumDeserializer extends JsonDeserializer<ErrorResponse<Integer>> {

    @Override
    public ResponseCode deserialize(JsonParser p, DeserializationContext deserializationContext) throws IOException {
        int typeValue = p.getIntValue();
        for (ResponseCode typeEnum : ResponseCode.values()) {
            if (typeValue == typeEnum.getCode()) {
                return typeEnum;
            }
        }
        return null;
    }
}

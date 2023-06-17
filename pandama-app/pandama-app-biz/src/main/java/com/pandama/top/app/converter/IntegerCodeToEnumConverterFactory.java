package com.pandama.top.app.converter;

import com.pandama.top.app.enums.BaseIntegerEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description: 枚举入参转换
 * @author: 王强
 * @dateTime: 2023-02-10 12:16:38
 */
@Component
@SuppressWarnings("all")
public class IntegerCodeToEnumConverterFactory implements ConverterFactory<Integer, BaseIntegerEnum> {

    private static final Map<Class, Converter> CONVERTERS = new ConcurrentHashMap<>();

    @Override
    public <T extends BaseIntegerEnum> Converter<Integer, T> getConverter(Class<T> targetType) {
        Converter<Integer, T> converter = CONVERTERS.get(targetType);
        if (null == converter) {
            converter = new StringToEnumConverter<>(targetType);
            CONVERTERS.put(targetType, converter);
        }
        return converter;
    }

    public static class StringToEnumConverter<T extends BaseIntegerEnum> implements Converter<Integer, T> {
        private Map<Integer, T> enumMap = new HashMap<>();

        public StringToEnumConverter(Class<T> enumType) {
            Arrays.stream(enumType.getEnumConstants()).forEach(x -> enumMap.put(x.getCode(), x));
        }

        @Override
        public T convert(Integer source) {
            return Optional.of(source)
                    .map(enumMap::get)
                    .orElseGet(() -> Optional.of(source)
                            .map(enumMap::get)
                            .orElseThrow(() -> new IllegalArgumentException("无法匹配对应的枚举类型: " + source)));
        }
    }
}

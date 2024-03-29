package com.pandama.top.app.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Objects;

/**
 * 存储操作类型枚举
 *
 * @author 王强
 * @date 2023-07-08 15:10:26
 */
@Getter
public enum StoreOperationTypeEnum implements BaseIntegerEnum {
    /**
     * 入库
     */
    IN(1, "入库"),
    /**
     * 出库
     */
    OUT(-1, "出库");

    @JsonValue
    private final Integer code;

    private final String name;

    StoreOperationTypeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer code) {
        StoreOperationTypeEnum[] values = StoreOperationTypeEnum.values();
        for (StoreOperationTypeEnum value : values) {
            if (Objects.equals(value.getCode(), code)) {
                return value.getName();
            }
        }
        return "";
    }

    public static StoreOperationTypeEnum get(Integer code) {
        StoreOperationTypeEnum[] values = StoreOperationTypeEnum.values();
        for (StoreOperationTypeEnum value : values) {
            if (Objects.equals(value.getCode(), code)) {
                return value;
            }
        }
        return null;
    }
}

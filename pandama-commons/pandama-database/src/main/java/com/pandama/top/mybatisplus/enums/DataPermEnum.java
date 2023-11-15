package com.pandama.top.mybatisplus.enums;

/**
 * 数据权限枚举类
 *
 * @author 王强
 * @date 2023-11-13 13:31:56
 */
public enum DataPermEnum {

    /**
     * 权限部门Id, sql中使用 in #{perm_dept_ids} 匹配
     */
    PERM_DEPT("perm_dept_ids"),
    ;

    /**
     * 数据权限字段名
     */
    private final String field;

    DataPermEnum(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}

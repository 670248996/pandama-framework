package com.pandama.top.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 所有实体类的基类
 * @author: 白剑民
 * @dateTime: 2022/10/17 15:54
 */
@Data
public class BaseEntity {

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 是否删除（逻辑），1，是；0，否。默认0（false）
     */
    @TableField(value = "is_delete")
    private Boolean isDelete;

    /**
     * 创建人id
     */
    @TableField(value = "create_user_id", fill = FieldFill.INSERT)
    private Long createUserId;

    /**
     * 创建人编号
     */
    @TableField(value = "create_user_code", fill = FieldFill.INSERT)
    private String createUserCode;

    /**
     * 创建人姓名
     */
    @TableField(value = "create_user_name", fill = FieldFill.INSERT)
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    @TableField(value = "update_user_id", fill = FieldFill.INSERT_UPDATE)
    private Long updateUserId;

    /**
     * 修改人编号
     */
    @TableField(value = "update_user_code", fill = FieldFill.INSERT_UPDATE)
    private String updateUserCode;

    /**
     * 修改人姓名
     */
    @TableField(value = "update_user_name", fill = FieldFill.INSERT_UPDATE)
    private String updateUserName;

    /**
     * 修改时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 乐观锁实现
     */
    @Version
    private Long version;

    public void clear() {
        setId(null);
        setCreateTime(null);
        setCreateUserId(null);
        setCreateUserCode(null);
        setCreateUserName(null);
        setUpdateTime(null);
        setUpdateUserId(null);
        setUpdateUserCode(null);
        setUpdateUserName(null);
    }
}

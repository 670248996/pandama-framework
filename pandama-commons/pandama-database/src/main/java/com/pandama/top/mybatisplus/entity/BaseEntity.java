package com.pandama.top.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
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
    @TableField("is_delete")
    private Boolean isDelete;

    /**
     * 创建人id
     */
    @TableField("create_user_id")
    private Long createUserId;

    /**
     * 创建人编号
     */
    @TableField("create_user_code")
    private String createUserCode;

    /**
     * 创建人姓名
     */
    @TableField("create_user_name")
    private String createUserName;

    /**
     * 创建时间
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 修改人id
     */
    @TableField("update_user_id")
    private Long updateUserId;

    /**
     * 修改人编号
     */
    @TableField("update_user_code")
    private String updateUserCode;

    /**
     * 修改人姓名
     */
    @TableField("update_user_name")
    private String updateUserName;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 乐观锁实现
     */
    @Version
    private Long version;
}

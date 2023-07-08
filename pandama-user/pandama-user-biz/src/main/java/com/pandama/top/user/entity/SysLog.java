package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import com.pandama.top.mybatisplus.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * 系统日志表
 *
 * @author 王强
 * @date 2023-07-08 15:47:10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SysLog extends BaseEntity {

    private static final long serialVersionUID = 2599924084478361697L;

    /**
     * 功能模块（字典表枚举值）
     */
    private String module;

    /**
     * 操作事件（字典表枚举值）
     */
    private String event;

    /**
     * 日志描述
     */
    private String msg;

    /**
     * 额外信息（备用）
     */
    private String extra;

    /**
     * 日志类型（1:系统日志，2:操作日志）
     */
    private Integer type;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 是否成功 默认1成功
     */
    private Boolean isSuccess;
}

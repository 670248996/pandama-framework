package com.pandama.top.user.api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 系统操作日志搜索回参
 * @author: 白剑民
 * @dateTime: 2022/11/21 09:35
 */
@Data
@ApiModel("系统操作日志搜索回参VO")
public class LogSearchResultVO {

    @ApiModelProperty("操作日志id")
    private Long id;

    @ApiModelProperty("序号，导出用，前端忽略")
    private Integer serialNo;

    @ApiModelProperty("操作人编号")
    private String createUserCode;

    @ApiModelProperty("操作人姓名")
    private String createUserName;

    @ApiModelProperty("操作时间(yyyy-MM-dd HH:mm:ss)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("操作事件")
    private String event;

    @ApiModelProperty("操作明细")
    private String msg;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "浏览器")
    private String browser;

}

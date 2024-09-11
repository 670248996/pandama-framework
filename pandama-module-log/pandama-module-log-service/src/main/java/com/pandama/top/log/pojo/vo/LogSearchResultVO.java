package com.pandama.top.log.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 日志搜索结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:38
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

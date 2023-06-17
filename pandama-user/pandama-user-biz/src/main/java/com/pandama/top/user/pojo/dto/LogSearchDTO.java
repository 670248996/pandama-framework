package com.pandama.top.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 系统操作日志搜索传参
 * @author: 白剑民
 * @dateTime: 2022/11/21 09:31
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel("系统操作日志搜索传参")
public class LogSearchDTO extends PageDTO {

    @ApiModelProperty(value = "日志类型", hidden = true)
    private LogTypeEnum logType;

    @ApiModelProperty("勾选的操作记录id列表")
    private List<Long> logIds;

    @ApiModelProperty(value = "事件")
    private String event;

    @ApiModelProperty("操作人")
    private String createUserCode;

    @ApiModelProperty("起始操作时间(yyyy-MM-dd HH:mm:ss)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("截止操作时间(yyyy-MM-dd HH:mm:ss)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreateTime;

}

package com.pandama.top.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.logRecord.enums.LogTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 系统操作日志导出传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:58
 */
@Data
@ApiModel("系统操作日志导出传参DTO")
public class LogExportDTO {

    @ApiModelProperty("日志类型")
    private LogTypeEnum logType;

    @ApiModelProperty("当前页")
    private Long current;

    @ApiModelProperty("每页大小")
    private Long size;

    @ApiModelProperty("勾选的操作记录id列表")
    private List<Long> logIds;

    @ApiModelProperty("操作人")
    private Long createUserId;

    @ApiModelProperty("起始操作时间(yyyy-MM-dd HH:mm:ss)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeStart;

    @ApiModelProperty("截止操作时间(yyyy-MM-dd HH:mm:ss)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTimeEnd;

}

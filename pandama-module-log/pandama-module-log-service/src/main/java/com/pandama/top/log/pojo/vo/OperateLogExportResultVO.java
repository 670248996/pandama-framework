package com.pandama.top.log.pojo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 操作日志导出结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:51
 */
@Data
@ApiModel("系统操作日志搜索回参VO")
public class OperateLogExportResultVO {

    @Excel(name = "序号", width = 15)
    private Integer serialNo;

    @Excel(name = "操作人编号", width = 15)
    private String createUserCode;

    @Excel(name = "操作人姓名", width = 15)
    private String createUserName;

    @Excel(name = "操作时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Excel(name = "操作事件", width = 15)
    private String event;

    @Excel(name = "操作明细", width = 50)
    private String msg;

}

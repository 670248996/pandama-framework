package com.pandama.top.user.pojo.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 登录日志导出结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:36
 */
@Data
@ApiModel("系统操作日志搜索回参VO")
public class LoginLogExportResultVO {

    @Excel(name = "序号", width = 10)
    private Integer serialNo;

    @Excel(name = "账号", width = 15)
    private String createUserCode;

    @Excel(name = "姓名", width = 15)
    private String createUserName;

    @Excel(name = "IP地址", width = 15)
    private String ipAddress;

    @Excel(name = "操作系统", width = 15)
    private String os;

    @Excel(name = "浏览器", width = 15)
    private String browser;

    @Excel(name = "登录状态", width = 15)
    private String event;

    @Excel(name = "描述", width = 80)
    private String msg;

    @Excel(name = "访问时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}

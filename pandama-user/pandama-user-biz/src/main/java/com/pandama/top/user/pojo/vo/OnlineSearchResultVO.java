package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 在线搜索结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:48
 */
@Data
@ApiModel("在线用户信息检索返回VO")
public class OnlineSearchResultVO {

    @ApiModelProperty(value = "在线id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "操作系统")
    private String os;

    @ApiModelProperty(value = "浏览器")
    private String browser;

    @ApiModelProperty("登陆时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;

}

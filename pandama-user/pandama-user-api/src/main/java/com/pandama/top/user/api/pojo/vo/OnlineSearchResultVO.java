package com.pandama.top.user.api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 在线用户信息检索返回结果
 * @author: 白剑民
 * @dateTime: 2022/10/26 15:08
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

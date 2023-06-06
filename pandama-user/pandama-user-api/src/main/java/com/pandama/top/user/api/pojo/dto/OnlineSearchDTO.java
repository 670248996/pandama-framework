package com.pandama.top.user.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @description: 用户信息检索传参
 * @author: 白剑民
 * @dateTime: 2022/10/26 14:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("在线用户检索DTO")
public class OnlineSearchDTO extends PageDTO {

    @ApiModelProperty(value = "事件", hidden = true)
    private String event;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty(value = "起始操作时间(yyyy-MM-dd HH:mm:ss)", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateTime;
}

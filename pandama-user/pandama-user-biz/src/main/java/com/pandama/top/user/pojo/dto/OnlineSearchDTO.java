package com.pandama.top.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 在线搜索入参
 *
 * @author 王强
 * @date 2023-07-08 15:50:40
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

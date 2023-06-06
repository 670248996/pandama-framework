package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 用户注册回参
 * @author: 白剑民
 * @dateTime: 2022/10/28 16:19
 */
@Data
@ApiModel("用户注册结果VO")
public class UserRegisterResultVO {

    @ApiModelProperty("用户id")
    private Long id;
}

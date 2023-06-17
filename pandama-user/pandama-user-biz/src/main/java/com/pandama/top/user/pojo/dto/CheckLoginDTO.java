package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 登录校验传参
 * @author: 白剑民
 * @dateTime: 2022/10/26 09:45
 */
@Data
@ApiModel("登录信息校验DTO")
public class CheckLoginDTO {

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

}

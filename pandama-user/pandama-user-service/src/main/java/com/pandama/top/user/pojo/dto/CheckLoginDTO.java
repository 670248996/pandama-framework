package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 登录校验传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:06
 */
@Data
@ApiModel("登录信息校验DTO")
public class CheckLoginDTO {

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

}

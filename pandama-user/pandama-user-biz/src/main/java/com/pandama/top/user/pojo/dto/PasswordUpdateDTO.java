package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 用户密码修改传参
 * @author: 白剑民
 * @dateTime: 2022/10/27 10:22
 */
@Data
@ApiModel("用户密码修改DTO")
public class PasswordUpdateDTO {

    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;

    @ApiModelProperty("是否需要校验旧密码")
    @NotNull(message = "是否需要校验旧密码，isNeedCheck不能为null")
    private Boolean isNeedCheck;

    @ApiModelProperty("新密码")
    @NotBlank(message = "新密码，newPassword不能为null")
    private String newPassword;

    @ApiModelProperty("原密码")
    private String oldPassword;

}

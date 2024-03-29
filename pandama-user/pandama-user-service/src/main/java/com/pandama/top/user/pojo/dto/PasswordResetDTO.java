package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 密码重置入参
 *
 * @author 王强
 * @date 2023-07-08 15:50:56
 */
@Data
@ApiModel("用户密码重置DTO")
public class PasswordResetDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户id，userIdId不能为null")
    private Long userId;

    @ApiModelProperty("新密码")
    @NotBlank(message = "密码，password不能为null")
    private String password;

}

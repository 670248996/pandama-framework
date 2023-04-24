package com.pandama.top.app.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 密码编辑入参
 * @author: 王强
 * @dateTime: 2023-04-24 21:35:16
 */
@Data
public class PasswordEditDTO {
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;
    @NotBlank(message = "新密码不能为空")
    private String newPassword;
}

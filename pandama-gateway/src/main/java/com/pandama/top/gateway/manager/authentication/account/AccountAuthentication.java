package com.pandama.top.gateway.manager.authentication.account;

import com.pandama.top.gateway.manager.authentication.BaseAuthentication;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 账号登录验证信息
 * @author: 王强
 * @dateTime: 2022-10-27 10:46:21
 */
@Data
public class AccountAuthentication implements BaseAuthentication {

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
    private String password;
}

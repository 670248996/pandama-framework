package com.pandama.top.pojo.dto;

import lombok.Data;

/**
 * @description: 用户名称登录入参
 * @author: 王强
 * @dateTime: 2023-04-20 21:43:55
 */
@Data
public class UsernameLoginDTO {
    private String username;
    private String password;
}

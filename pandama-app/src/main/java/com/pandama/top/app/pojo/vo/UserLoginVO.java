package com.pandama.top.app.pojo.vo;

import lombok.Data;

/**
 * @description: 用户信息出参
 * @author: 王强
 * @dateTime: 2023-04-19 12:10:00
 */
@Data
public class UserLoginVO {
    private Long id;
    private String username;
    private String password;
    private String phoneNumber;
}

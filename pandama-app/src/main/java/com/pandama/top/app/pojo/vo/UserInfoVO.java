package com.pandama.top.app.pojo.vo;

import lombok.Data;

import java.time.LocalDate;

/**
 * @description: 用户信息出参
 * @author: 王强
 * @dateTime: 2023-04-19 12:10:00
 */
@Data
public class UserInfoVO {
    private String username = "admin";
    private String nickName = "林林";
    private String phoneNumber = "18888888888";
    private String email = "123456@qq.com";
    private String jobName = "销售";
    private String roleName = "管理员";
    private Integer gender = 0;
    private LocalDate createTime = LocalDate.now();
}

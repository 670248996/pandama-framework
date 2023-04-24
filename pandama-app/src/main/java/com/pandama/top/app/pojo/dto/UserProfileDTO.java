package com.pandama.top.app.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @description: 用户信息出参
 * @author: 王强
 * @dateTime: 2023-04-19 12:10:00
 */
@Data
public class UserProfileDTO {
    @NotNull(message = "用户ID不能为null")
    private Long id;
    private String username;
    private String nickName;
    private String phoneNumber;
    private String email;
    private String postName;
    private String roleName;
    private Integer gender;
}

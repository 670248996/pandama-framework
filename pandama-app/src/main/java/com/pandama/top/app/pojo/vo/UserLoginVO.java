package com.pandama.top.app.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

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
    private String nickName;
    private String realName;
    private String phoneNumber;
    private String email;
    private String occupationName;
    private String jobName;
    private String roleName;
    private Integer gender;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",shape = JsonFormat.Shape.STRING,timezone = "GMT+8")
    private LocalDateTime passwordExpireTime;
}

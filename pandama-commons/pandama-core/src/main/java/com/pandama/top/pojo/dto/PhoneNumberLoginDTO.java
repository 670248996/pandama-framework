package com.pandama.top.pojo.dto;

import lombok.Data;

/**
 * @description: 手机号码登录入参
 * @author: 王强
 * @dateTime: 2023-04-20 21:43:35
 */
@Data
public class PhoneNumberLoginDTO {
    private String phoneNumber;
    private String smsCode;
}

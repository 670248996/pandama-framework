package com.pandama.top.app.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @description: 用户
 * @author: 王强
 * @dateTime: 2023-04-20 14:44:22
 */
@Data
public class User extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 编号
     */
    private String nickName;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 联系电话
     */
    private String phoneNumber;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 职业
     */
    private String occupationCode;
    /**
     * 职业
     */
    private String occupationName;
    /**
     * 职业
     */
    private String postCode;
    /**
     * 职业
     */
    private String postName;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 是否可用(0: 不可用, 1: 可用)
     */
    private Boolean isEnable;
    /**
     * 密码到期时间
     */
    private LocalDateTime passwordExpireTime;
    /**
     * 备注
     */
    private String remark;
}

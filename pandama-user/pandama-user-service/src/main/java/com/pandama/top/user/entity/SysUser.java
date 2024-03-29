package com.pandama.top.user.entity;

import com.pandama.top.mybatisplus.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 用户信息表
 *
 * @author 王强
 * @date 2023-07-08 15:47:31
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class SysUser extends BaseEntity {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户编号
     */
    private String userCode;
    /**
     * 真实姓名
     */
    private String realName;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 身份证号码
     */
    private String idCardNo;
    /**
     * 性别
     */
    private Integer gender;
    /**
     * 出生日期
     */
    private LocalDate birthday;
    /**
     * 用户头像
     */
    private String avatar;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 学历（字典表枚举）
     */
    private Integer education;
    /**
     * 籍贯
     */
    private String nativePlace;
    /**
     * 职业
     */
    private String occupation;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 工作地
     */
    private String workPlace;
    /**
     * 在职状态（字典表枚举）
     */
    private Integer workingState;
    /**
     * 入职日期
     */
    private LocalDate onboardDate;
    /**
     * 是否可用(0: 不可用, 1: 可用)
     */
    private Boolean status;
    /**
     * 是否为管理员(0: 否, 1: 是)
     */
    private Boolean isAdmin;
    /**
     * 密码到期时间
     */
    private LocalDateTime passwordExpireTime;
    /**
     * 备注
     */
    private String remark;
}

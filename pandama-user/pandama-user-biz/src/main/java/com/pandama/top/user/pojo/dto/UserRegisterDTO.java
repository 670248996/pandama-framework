package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 用户注册传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:52
 */
@Data
@ApiModel("用户注册DTO")
public class UserRegisterDTO {

    @ApiModelProperty("用户账号(若不传此字段，则默认使用联系方式作为账号)")
    @NotBlank(message = "用户账号，username不能为null")
    private String username;

    @ApiModelProperty("用户密码")
    @NotBlank(message = "用户密码，password不能为null")
    private String password;

    @ApiModelProperty("用户姓名")
    @NotBlank(message = "用户姓名，realName不能为null")
    private String realName;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("编号/工号")
    private String code;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("手机号")
    @NotBlank(message = "手机号，phone不能为null")
    private String phone;

    @ApiModelProperty("所属部门id")
    @NotNull(message = "部门id，deptId不能为null")
    private Long deptId;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("备注")
    private String remark;

}

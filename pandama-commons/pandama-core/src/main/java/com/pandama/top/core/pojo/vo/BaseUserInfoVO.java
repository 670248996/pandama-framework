package com.pandama.top.core.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 基地用户信息出参
 * @author: 王强
 * @dateTime: 2023-05-24 12:15:37
 */
@Data
public class BaseUserInfoVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("编号")
    private String code;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("所属部门id")
    private Long deptId;

    @ApiModelProperty("所属部门名称")
    private String deptName;

    @ApiModelProperty("所属部门编号")
    private String deptCode;

    @ApiModelProperty("所属企业/机构id")
    private Long enterpriseId;

    @ApiModelProperty("所属企业/机构全称")
    private String enterpriseFullName;

    @ApiModelProperty("ip地址")
    private String ipAddress;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;
}

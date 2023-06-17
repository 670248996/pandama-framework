package com.pandama.top.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @description: 用户信息检索传参
 * @author: 白剑民
 * @dateTime: 2022/10/26 14:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户信息检索DTO")
public class UserSearchDTO extends PageDTO {

    @ApiModelProperty("企业/机构名称")
    private String enterpriseName;

    @ApiModelProperty("部门ID")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("用户编号")
    private String code;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("在职状态（字典表枚举）")
    private Integer workingState;

    @ApiModelProperty("账号是否可用")
    private Boolean status;

    @ApiModelProperty("是否为管理员")
    private Boolean isAdmin;

    @ApiModelProperty("创建时间起始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("创建时间截止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreateTime;
}

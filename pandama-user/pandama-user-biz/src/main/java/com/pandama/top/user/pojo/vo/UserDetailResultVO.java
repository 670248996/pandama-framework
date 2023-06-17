package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @description: 用户信息VO
 * @author: 白剑民
 * @dateTime: 2022/10/21 17:19
 */
@Data
@ApiModel("用户信息VO")
public class UserDetailResultVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("编号")
    private String userCode;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("身份证号")
    private String idCardNo;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ApiModelProperty("年龄")
    private Integer age;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("部门id")
    private Long deptId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("账号是否启用")
    private Boolean status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}

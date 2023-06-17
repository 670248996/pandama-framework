package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 用户信息检索返回结果
 * @author: 白剑民
 * @dateTime: 2022/10/26 15:08
 */
@Data
@ApiModel("用户信息检索返回结果VO")
public class UserSearchResultVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("编号")
    private String userCode;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("用户头像地址")
    private String avatar;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("在职状态（字典表枚举）")
    private String workingState;

    @ApiModelProperty("是否为管理员")
    private Boolean isAdmin;

    @ApiModelProperty("账号是否可用")
    private Boolean status;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("起始创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("截止创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}

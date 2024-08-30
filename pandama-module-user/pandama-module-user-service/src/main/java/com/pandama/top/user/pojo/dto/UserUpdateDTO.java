package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户信息更新传参
 *
 * @author 王强
 * @date 2023-07-08 15:52:03
 */
@Data
@ApiModel("用户信息更新DTO")
public class UserUpdateDTO {

    @ApiModelProperty("用户ID")
    @NotNull(message = "用户id，userId不能为null")
    private Long userId;

    @ApiModelProperty("用户编号")
    private String code;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("在职状态（字典表枚举）")
    private Integer workingState;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("部门ID")
    private Long deptId;
}

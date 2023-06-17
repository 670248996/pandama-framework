package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 用户授权角色传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:24
 */
@Data
@ApiModel("用户授权角色传参DTO")
public class UserAuthRoleConfirmDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIds;

}

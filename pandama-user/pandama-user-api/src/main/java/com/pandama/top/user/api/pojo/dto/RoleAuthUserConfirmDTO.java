package com.pandama.top.user.api.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 角色授权用户传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:24
 */
@Data
@ApiModel("角色授权用户传参DTO")
public class RoleAuthUserConfirmDTO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户ID列表")
    private List<Long> userIds;

}

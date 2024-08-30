package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色授权用户传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:12
 */
@Data
@ApiModel("角色授权用户传参DTO")
public class RoleAuthUserConfirmDTO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户ID列表")
    private List<Long> userIds;

}

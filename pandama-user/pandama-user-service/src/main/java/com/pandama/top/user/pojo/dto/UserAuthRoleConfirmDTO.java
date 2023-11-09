package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户授权角色传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:42
 */
@Data
@ApiModel("用户授权角色传参DTO")
public class UserAuthRoleConfirmDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIds;

}

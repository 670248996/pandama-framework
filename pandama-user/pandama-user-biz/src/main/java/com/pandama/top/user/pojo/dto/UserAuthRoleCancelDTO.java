package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户取消授权角色传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:37
 */
@Data
@ApiModel("用户取消授权角色传参DTO")
public class UserAuthRoleCancelDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色ID列表")
    private List<Long> roleIds;

}

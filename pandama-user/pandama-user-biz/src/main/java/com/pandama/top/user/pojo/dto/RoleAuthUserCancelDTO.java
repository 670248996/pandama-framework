package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色取消授权用户传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:06
 */
@Data
@ApiModel("角色取消授权用户传参DTO")
public class RoleAuthUserCancelDTO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户ID列表")
    private List<Long> userIds;

}

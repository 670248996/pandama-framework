package com.pandama.top.user.api.pojo.dto;

import com.pandama.top.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 用户查询授权角色传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("用户查询授权角色传参DTO")
public class UserAuthRoleSearchDTO extends PageDTO {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("角色名称")
    private String roleName;

}

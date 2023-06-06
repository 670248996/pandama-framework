package com.pandama.top.user.api.pojo.dto;

import com.pandama.top.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 角色查询授权用户传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色查询授权用户传参DTO")
public class RoleAuthUserSearchDTO extends PageDTO {

    @ApiModelProperty("角色ID")
    private Long roleId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("手机号")
    private String phone;

}

package com.pandama.top.user.pojo.dto;

import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询授权用户传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:18
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

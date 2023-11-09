package com.pandama.top.user.pojo.dto;

import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户查询授权角色传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:47
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

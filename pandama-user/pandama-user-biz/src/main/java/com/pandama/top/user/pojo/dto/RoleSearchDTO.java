package com.pandama.top.user.pojo.dto;

import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 角色查询传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("角色查询传参DTO")
public class RoleSearchDTO extends PageDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编号")
    private String roleCode;

    @ApiModelProperty("是否启用")
    private Boolean status;

}

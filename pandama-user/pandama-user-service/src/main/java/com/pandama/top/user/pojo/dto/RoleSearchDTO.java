package com.pandama.top.user.pojo.dto;

import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色查询传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:27
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

package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色查询回参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:23
 */
@Data
@ApiModel("角色查询回参VO")
public class RoleCreateResultVO {

    @ApiModelProperty("角色id")
    private Long id;

}

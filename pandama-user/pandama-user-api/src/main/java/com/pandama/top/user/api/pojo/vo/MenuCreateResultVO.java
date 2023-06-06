package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 角色创建回参
 * @author: 白剑民
 * @dateTime: 2022/10/31 17:28
 */
@Data
@ApiModel("权限创建回参VO")
public class MenuCreateResultVO {

    @ApiModelProperty("权限id")
    private Long id;

}

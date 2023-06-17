package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 菜单信息查询传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 17:45
 */
@Data
@ApiModel("菜单信息查询DTO")
public class MenuSearchDTO {

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("排除父节点id")
    private Long excludeParentId;

}

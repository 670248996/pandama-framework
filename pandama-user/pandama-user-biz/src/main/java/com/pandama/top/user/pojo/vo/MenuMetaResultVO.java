package com.pandama.top.user.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 菜单Meta信息出参
 * @author: 王强
 * @dateTime: 2023-04-24 17:03:45
 */
@Data
@ApiModel("菜单Meta信息")
public class MenuMetaResultVO {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("组件")
    private String component;

    @ApiModelProperty("图标")
    private String icon;
}

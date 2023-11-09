package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单Meta信息入参
 *
 * @author 王强
 * @date 2023-07-08 15:50:14
 */
@Data
@ApiModel("菜单Meta信息")
public class MenuMetaDTO {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("组建")
    private String component;

    @ApiModelProperty("图标")
    private String icon;
}

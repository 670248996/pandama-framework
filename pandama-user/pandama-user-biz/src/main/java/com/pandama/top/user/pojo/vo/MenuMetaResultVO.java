package com.pandama.top.user.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单元结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:42
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

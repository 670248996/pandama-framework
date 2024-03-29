package com.pandama.top.user.pojo.dto;

import com.pandama.top.user.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 菜单信息更新传参
 *
 * @author 王强
 * @date 2023-07-08 15:50:27
 */
@Data
@ApiModel("菜单信息更新DTO")
public class MenuUpdateDTO {

    @ApiModelProperty("菜单id")
    @NotNull(message = "菜单id，id不能为null")
    private Long id;

    @ApiModelProperty("父级部门id")
    @NotNull(message = "父级部门id，parentId不能为null")
    private Long parentId;

    @ApiModelProperty("菜单类型（字典表枚举）")
    @NotNull(message = "菜单类型，menuType不能为null")
    private MenuTypeEnum menuType;

    @ApiModelProperty("菜单名称")
    @NotBlank(message = "菜单名称，menuName不能为null")
    private String menuName;

    @ApiModelProperty("菜单编号")
    private String menuCode;

    @ApiModelProperty("菜单路径")
    private String menuUrl;

    @ApiModelProperty("菜单参数")
    private String menuParams;

    @ApiModelProperty("meta原数据")
    private MenuMetaDTO meta;

    @ApiModelProperty("是否可用")
    private Boolean status;

}

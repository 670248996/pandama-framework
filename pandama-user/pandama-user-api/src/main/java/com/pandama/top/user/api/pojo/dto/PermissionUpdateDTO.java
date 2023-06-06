package com.pandama.top.user.api.pojo.dto;

import com.pandama.top.user.api.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 权限信息更新传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 17:45
 */
@Data
@ApiModel("权限信息更新DTO")
public class PermissionUpdateDTO {

    @ApiModelProperty("权限id")
    @NotNull(message = "权限id，permissionId不能为null")
    private Long menuId;

    @ApiModelProperty("父级部门id")
    @NotNull(message = "父级部门id，parentId不能为null")
    private Long parentId;

    @ApiModelProperty("权限类型（数据字典表枚举）")
    @NotNull(message = "权限类型，permissionType不能为null")
    private MenuTypeEnum menuType;

    @ApiModelProperty("权限名称")
    @NotBlank(message = "权限名称，permissionName不能为null")
    private String menuName;

    @ApiModelProperty("权限编号")
    private String menuCode;

    @ApiModelProperty("权限路径")
    private String menuUrl;

    @ApiModelProperty("权限参数")
    private String menuParams;

    @ApiModelProperty("meta原数据")
    private PermissionMetaDTO meta;

    @ApiModelProperty("是否可用")
    private Boolean status;

}

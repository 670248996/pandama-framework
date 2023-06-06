package com.pandama.top.user.api.pojo.dto;

import com.pandama.top.user.api.enums.MenuTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 创建权限传参
 * @author: 白剑民
 * @dateTime: 2022/10/31 17:30
 */
@Data
@ApiModel("创建权限传参DTO")
public class PermissionCreateDTO {

    @ApiModelProperty("父级权限id")
    @NotNull(message = "父级权限id，parentId不能为null")
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

    @ApiModelProperty("顺序")
    private Integer orderNum;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("meta原数据")
    private PermissionMetaDTO meta;

}

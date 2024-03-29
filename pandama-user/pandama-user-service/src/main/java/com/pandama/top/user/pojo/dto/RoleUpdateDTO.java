package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 角色信息更新传参
 *
 * @author 王强
 * @date 2023-07-08 15:51:32
 */
@Data
@ApiModel("角色信息更新DTO")
public class RoleUpdateDTO {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id，id不能为null")
    private Long id;

    @ApiModelProperty("角色名称")
    @NotBlank(message = "角色名称，roleName不能为null")
    private String roleName;

    @ApiModelProperty("角色编号")
    @NotBlank(message = "角色编号，roleCode不能为null")
    private String roleCode;

    @ApiModelProperty("角色等级")
    private Integer level;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("菜单id列表")
    private List<Long> menuIds;

    @ApiModelProperty("备注")
    private String remark;

}

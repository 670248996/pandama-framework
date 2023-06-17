package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 部门信息更新传参
 * @author: 白剑民
 * @dateTime: 2022/10/25 09:42
 */
@Data
@ApiModel("部门信息更新传参DTO")
public class DeptUpdateDTO {

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id，deptId不能为null")
    private Long deptId;

    @ApiModelProperty("父级部门id")
    @NotNull(message = "父级部门id，parentId不能为null")
    private Long parentId;

    @ApiModelProperty("部门名称")
    @NotBlank(message = "部门名称，departmentName不能为null")
    private String deptName;

    @ApiModelProperty("部门编号")
    private String deptCode;

    @ApiModelProperty("负责人名称")
    private String managerName;

    @ApiModelProperty("负责人手机号")
    private String managerPhone;

    @ApiModelProperty("负责人邮箱")
    private String managerEmail;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否可用")
    private Boolean status;
}

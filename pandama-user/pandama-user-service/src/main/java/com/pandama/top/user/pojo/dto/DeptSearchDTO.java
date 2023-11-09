package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 查询部门传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:16
 */
@Data
@ApiModel("查询部门传参DTO")
public class DeptSearchDTO {

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门状态")
    private Boolean status;

    @ApiModelProperty("排除父节点id")
    private Long excludeParentId;

}

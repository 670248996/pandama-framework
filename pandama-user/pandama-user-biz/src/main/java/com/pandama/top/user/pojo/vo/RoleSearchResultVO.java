package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: 角色创建回参
 * @author: 白剑民
 * @dateTime: 2022/10/31 16:23
 */
@Data
@ApiModel("角色创建回参VO")
public class RoleSearchResultVO {

    @ApiModelProperty("角色id")
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色编号")
    private String roleCode;

    @ApiModelProperty("角色等级")
    private Integer level;

    @ApiModelProperty("是否可用(0: 不可用, 1: 可用)")
    private Boolean status;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}

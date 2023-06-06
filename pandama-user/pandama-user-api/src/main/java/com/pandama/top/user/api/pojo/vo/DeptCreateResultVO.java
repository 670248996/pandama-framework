package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 创建部门回参
 * @author: 白剑民
 * @dateTime: 2022/10/25 09:38
 */
@Data
@ApiModel("创建部门回参VO")
public class DeptCreateResultVO {

    @ApiModelProperty("部门id")
    private Long id;

}

package com.pandama.top.user.api.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @description: 数据字典创建传参
 * @author: 白剑民
 * @dateTime: 2023/5/4 09:21
 */
@Data
@ApiModel("创建数据字典传参")
public class DictCreateDTO {

    @ApiModelProperty("上级字典id")
    private Long dictParentId;

    @NotBlank(message = "数据字典类型，dictType不能为null")
    @ApiModelProperty("数据字典类型")
    private String dictType;

    @NotBlank(message = "数据字典代码，dictCode不能为null")
    @ApiModelProperty("数据字典代码")
    private String dictCode;

    @NotBlank(message = "数据字典名称，dictName不能为null")
    @ApiModelProperty("数据字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;

}

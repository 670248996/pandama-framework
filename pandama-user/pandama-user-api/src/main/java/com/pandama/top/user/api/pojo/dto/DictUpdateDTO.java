package com.pandama.top.user.api.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: 数据字典更新
 * @author: 白剑民
 * @dateTime: 2023/5/22 11:02
 */
@Data
@ApiModel("数据字典更新传参")
public class DictUpdateDTO {

    @NotNull(message = "数据字典主键id，dictId不能为null")
    @ApiModelProperty("数据字典主键id")
    private Long dictId;

    @ApiModelProperty("数据字典类型")
    private String dictType;

    @ApiModelProperty("数据字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;
}

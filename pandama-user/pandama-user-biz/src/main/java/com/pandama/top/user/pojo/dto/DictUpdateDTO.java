package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 字典更新传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:35
 */
@Data
@ApiModel("字典更新传参")
public class DictUpdateDTO {

    @NotNull(message = "字典id，id不能为null")
    @ApiModelProperty("字典id")
    private Long id;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;
}

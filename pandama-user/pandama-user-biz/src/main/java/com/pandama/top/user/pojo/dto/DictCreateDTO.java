package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 创建字典传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:26
 */
@Data
@ApiModel("创建字典传参")
public class DictCreateDTO {

    @ApiModelProperty("上级字典id")
    private Long parentId;

    @NotBlank(message = "字典代码，dictCode不能为null")
    @ApiModelProperty("字典代码")
    private String dictCode;

    @NotBlank(message = "字典名称，dictName不能为null")
    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;

}

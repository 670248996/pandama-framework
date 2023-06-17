package com.pandama.top.user.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 字典信息
 * @author: 白剑民
 * @dateTime: 2023/5/20 21:14
 */
@Data
@ApiModel("字典信息")
public class DictDetailResultVO {

    @ApiModelProperty("字典主键id")
    private Long id;

    @ApiModelProperty("上级字典id")
    private Long parentId;

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典代码")
    private String dictCode;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("字典等级")
    private Integer level;
}
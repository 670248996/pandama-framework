package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 数据字典创建返回值
 * @author: 白剑民
 * @dateTime: 2023/5/20 21:06
 */
@Data
@ApiModel("数据字典创建返回值")
public class DictCreateResultVO {
    @ApiModelProperty("数据字典主键id")
    private Long id;
}

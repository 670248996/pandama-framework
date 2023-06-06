package com.pandama.top.user.api.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @description: 数据字典搜索传参
 * @author: 白剑民
 * @dateTime: 2023/5/2 20:42
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("数据字典搜索传参")
public class DictSearchDTO extends PageDTO {

    @ApiModelProperty("数据字典类型")
    private String dictType;

    @ApiModelProperty("数据字典代码")
    private String dictCode;

    @ApiModelProperty("数据字典名称")
    private String dictName;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("数据字典等级")
    private Integer level;

    @ApiModelProperty("创建时间起始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("创建时间截止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreateTime;

}

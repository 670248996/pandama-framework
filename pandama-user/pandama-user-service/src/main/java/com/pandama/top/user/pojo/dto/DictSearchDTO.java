package com.pandama.top.user.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 字典搜索传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("字典搜索传参")
public class DictSearchDTO extends PageDTO {

    @ApiModelProperty("字典类型")
    private String dictType;

    @ApiModelProperty("字典代码")
    private String dictCode;

    @ApiModelProperty("字典名称")
    private String dictName;

    @ApiModelProperty("字典状态")
    private Boolean status;

    @ApiModelProperty("字典等级")
    private Integer level;

    @ApiModelProperty("创建时间起始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startCreateTime;

    @ApiModelProperty("创建时间截止")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endCreateTime;

}

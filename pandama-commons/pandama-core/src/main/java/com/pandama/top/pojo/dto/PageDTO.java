package com.pandama.top.pojo.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 分页查询基础类
 * @author: 白剑民
 * @date : 2022/7/8 14:44
 */
@Data
public class PageDTO {

    @ApiModelProperty("当前页")
    private Long current;

    @ApiModelProperty("每页大小")
    private Long size;

    public PageDTO() {
        this.current = 1L;
        this.size = 10L;
    }
}

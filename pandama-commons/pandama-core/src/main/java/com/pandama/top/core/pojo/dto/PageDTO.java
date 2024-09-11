package com.pandama.top.core.pojo.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页查询基础类
 *
 * @author 王强
 * @date 2023-07-08 15:13:04
 */
@Data
public class PageDTO {

    @ApiModelProperty("当前页")
    private Long current;

    @ApiModelProperty("每页大小")
    private Long size;

    @ApiModelProperty("自定义排序")
    private List<Sort> sorts;

    public PageDTO() {
        this.current = 1L;
        this.size = 10L;
        this.sorts = new ArrayList<>();
    }
}

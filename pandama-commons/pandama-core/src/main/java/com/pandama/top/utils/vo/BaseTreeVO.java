package com.pandama.top.utils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 树结构的基类
 * @author: 王强
 * @dateTime: 2022-07-29 09:51:33
 */
@Data
public class BaseTreeVO<T extends BaseTreeVO> implements Serializable {

    private static final long serialVersionUID = 8481964221820200858L;

    @ApiModelProperty("节点id")
    private Long id;

    @ApiModelProperty("父节点id")
    private Long parentId;

    @ApiModelProperty("子集列表（树形结构展示用）")
    private List<T> children;

}

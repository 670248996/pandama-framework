package com.pandama.top.core.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 树出参
 *
 * @author 王强
 * @date 2023-07-08 15:13:20
 */
@Data
public class TreeVO implements Serializable {

    private static final long serialVersionUID = 8481964221820200858L;

    @ApiModelProperty("节点id")
    private Long id;

    @ApiModelProperty("父节点id")
    private Long parentId;

    @ApiModelProperty("子集列表（树形结构展示用）")
    private List<? extends TreeVO> children;

}

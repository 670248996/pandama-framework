package com.pandama.top.user.pojo.vo;

import com.pandama.top.core.pojo.vo.TreeVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 树数据下拉的返回
 * @author: 王强
 * @dateTime: 2022-07-29 09:51:33
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeSelectVO implements Serializable {

    private static final long serialVersionUID = 8481964221820200858L;

    @ApiModelProperty("选中的keys")
    private List<Long> selectedKeys;

    @ApiModelProperty("树列表（树形结构展示用）")
    private List<? extends TreeVO> treeList;

}

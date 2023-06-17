package com.pandama.top.user.pojo.vo;

import com.pandama.top.user.enums.MenuTypeEnum;
import com.pandama.top.core.pojo.vo.TreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 菜单树VO
 * @author: 白剑民
 * @dateTime: 2022/10/31 14:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("菜单树VO")
public class MenuTreeVO extends TreeVO {

    private static final long serialVersionUID = -2094863636304329063L;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单类型")
    private MenuTypeEnum menuType;

    @ApiModelProperty("菜单编码")
    private String menuCode;

    @ApiModelProperty("菜单路径")
    private String menuUrl;

    @ApiModelProperty("菜单参数")
    private String menuParams;

    @ApiModelProperty("元数据")
    private String meta;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("终端类型")
    private Integer terminalType;

    @ApiModelProperty("备注")
    private String remark;
}

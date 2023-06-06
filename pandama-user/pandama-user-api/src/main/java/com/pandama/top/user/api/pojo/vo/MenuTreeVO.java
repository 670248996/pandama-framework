package com.pandama.top.user.api.pojo.vo;

import com.pandama.top.user.api.enums.MenuTypeEnum;
import com.pandama.top.utils.BaseTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 权限树VO
 * @author: 白剑民
 * @dateTime: 2022/10/31 14:47
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("权限树VO")
public class MenuTreeVO extends BaseTreeVO {

    private static final long serialVersionUID = -2094863636304329063L;

    @ApiModelProperty("权限名称")
    private String menuName;

    @ApiModelProperty("权限类型")
    private MenuTypeEnum menuType;

    @ApiModelProperty("权限编码")
    private String menuCode;

    @ApiModelProperty("权限路径")
    private String menuUrl;

    @ApiModelProperty("权限参数")
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

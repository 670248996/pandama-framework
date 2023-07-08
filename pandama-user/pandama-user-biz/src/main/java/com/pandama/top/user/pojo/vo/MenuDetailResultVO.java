package com.pandama.top.user.pojo.vo;

import com.pandama.top.user.enums.MenuTypeEnum;
import com.pandama.top.user.pojo.dto.MenuMetaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 菜单详情结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:40
 */
@Data
@ApiModel("菜单详情VO")
public class MenuDetailResultVO {

    private static final long serialVersionUID = -2094863636304329063L;

    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("父级菜单id")
    private Long parentId;

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

    @ApiModelProperty("meta原数据")
    private MenuMetaDTO meta;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("终端类型")
    private Integer terminalType;

    @ApiModelProperty("备注")
    private String remark;
}

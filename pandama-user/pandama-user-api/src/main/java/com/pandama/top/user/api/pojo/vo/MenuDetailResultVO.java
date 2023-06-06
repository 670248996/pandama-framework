package com.pandama.top.user.api.pojo.vo;

import com.pandama.top.user.api.enums.MenuTypeEnum;
import com.pandama.top.user.api.pojo.dto.PermissionMetaDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 权限详情VO
 * @author: 王强
 * @dateTime: 2022/10/31 14:47
 */
@Data
@ApiModel("权限详情VO")
public class MenuDetailResultVO {

    private static final long serialVersionUID = -2094863636304329063L;

    @ApiModelProperty("权限id")
    private Long id;

    @ApiModelProperty("父级权限id")
    private Long parentId;

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

    @ApiModelProperty("meta原数据")
    private PermissionMetaDTO meta;

    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("终端类型")
    private Integer terminalType;

    @ApiModelProperty("备注")
    private String remark;
}

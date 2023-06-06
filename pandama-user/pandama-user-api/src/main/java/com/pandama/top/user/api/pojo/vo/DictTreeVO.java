package com.pandama.top.user.api.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pandama.top.utils.BaseTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @description: 数据字典树列表VO
 * @author: 白剑民
 * @dateTime: 2023/5/22 13:42
 */
@EqualsAndHashCode(callSuper = false)
@Data
@ApiModel("数据字典树列表")
public class DictTreeVO extends BaseTreeVO {

    private static final long serialVersionUID = -174319229475295487L;

    @ApiModelProperty("数据字典类型")
    private String dictType;

    @ApiModelProperty("数据字典代码")
    private String dictCode;

    @ApiModelProperty("数据字典名称")
    private String dictName;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("是否启用")
    private Boolean status;

    @ApiModelProperty("数据字典等级")
    private Integer level;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty("创建人姓名")
    private String createUserName;

    @ApiModelProperty("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @ApiModelProperty("修改人姓名")
    private String updateUserName;
}

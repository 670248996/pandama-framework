package com.pandama.top.user.api.pojo.vo;

import com.pandama.top.utils.BaseTreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @description: 部门树VO
 * @author: 白剑民
 * @dateTime: 2022/10/21 15:21
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("部门树VO")
public class DeptTreeVO extends BaseTreeVO {

    private static final long serialVersionUID = 1996043039608282286L;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("备注")
    private String remark;

}

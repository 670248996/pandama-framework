package com.pandama.top.user.pojo.vo;

import com.pandama.top.core.pojo.vo.TreeVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门树出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("部门树VO")
public class DeptTreeVO extends TreeVO {

    private static final long serialVersionUID = 1996043039608282286L;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("备注")
    private String remark;

}

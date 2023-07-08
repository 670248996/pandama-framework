package com.pandama.top.user.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 部门搜索结果出参
 *
 * @author 王强
 * @date 2023-07-08 15:52:23
 */
@Data
@ApiModel("部门查询VO")
public class DeptSearchResultVO {

    private static final long serialVersionUID = 1996043039608282286L;

    @ApiModelProperty("部门id")
    private Long id;

    @ApiModelProperty("父级部门id")
    private Long parentId;

    @ApiModelProperty("部门名称")
    private String deptName;

    @ApiModelProperty("部门编码")
    private String deptCode;

    @ApiModelProperty("是否可用")
    private Boolean status;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

}

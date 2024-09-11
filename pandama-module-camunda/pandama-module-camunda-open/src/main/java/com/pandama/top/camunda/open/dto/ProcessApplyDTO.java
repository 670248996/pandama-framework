package com.pandama.top.camunda.open.dto;

import com.pandama.top.camunda.open.interfaces.ProcessVariable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流程审批信息
 *
 * @author 王强
 * @date 2023-07-08 15:39:59
 */
@Data
@ApiModel("流程审批信息")
public class ProcessApplyDTO implements ProcessVariable {

    @ApiModelProperty("评论")
    private String comment;
}

package com.pandama.top.camunda.open.dto;

import com.pandama.top.camunda.open.interfaces.ProcessVariable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 启动请假流程信息
 *
 * @author 王强
 * @date 2023-07-08 15:39:59
 */
@Data
@ApiModel("启动请假流程信息")
public class ProcessStartLeaveDTO implements ProcessVariable {

    @ApiModelProperty("用户 ID")
    private String userId;

    @ApiModelProperty("上级 ID 集合")
    private List<String> leaderIds;
}

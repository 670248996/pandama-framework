package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 密码策略生成DTO
 *
 * @author 王强
 * @date 2023-07-08 15:50:51
 */
@Data
@ApiModel("密码策略DTO")
public class PasswordPolicyDTO {

    @ApiModelProperty("密码策略id")
    @NotNull(message = "密码策略id，passwordPolicyId不能为null")
    private Long passwordPolicyId;

    @ApiModelProperty("密码复杂类型（字典表枚举，默认：0，简单类型）")
    private Integer complexType;

    @ApiModelProperty("密码校验表达式（前端使用该表达式校验）")
    private String expression;

    @ApiModelProperty("密码有效期(单位：天，默认：0，永不过期)")
    private Integer validityPeriod;

    @ApiModelProperty("过期提醒阈值(单位：天，默认：0，不提醒")
    private Integer remindThreshold;

    @ApiModelProperty("重试次数(默认3次)")
    private Integer retryNum;

    @ApiModelProperty("冻结时长(单位: 小时，默认：24小时)")
    private Integer freezeTime;

    @ApiModelProperty("备注")
    private String remark;
}

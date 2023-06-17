package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @description: 企业/机构信息更新传参
 * @author: 白剑民
 * @dateTime: 2022/10/24 10:39
 */
@Data
@ApiModel("企业/机构信息更新DTO")
public class EnterpriseUpdateDTO {

    @ApiModelProperty("企业/机构id")
    @NotNull(message = "企业/机构id，enterpriseId不能为null")
    private Long enterpriseId;

    @ApiModelProperty("企业/机构简称")
    private String shortName;

    @ApiModelProperty("所在省份")
    private String province;

    @ApiModelProperty("所在城市")
    private String city;

    @ApiModelProperty("所在区/县")
    private String county;

    @ApiModelProperty("所在详细地址")
    private String address;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("法定代表人")
    private String legalPerson;

    @ApiModelProperty("法定代表人联系方式")
    private String legalPersonPhone;

    @ApiModelProperty("企业/机构负责人")
    private String charger;

    @ApiModelProperty("负责人联系方式")
    private String chargerPhone;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("密码策略id")
    private Long passwordPolicyId;
}

package com.pandama.top.user.pojo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @description: 企业/机构注册传参
 * @author: 白剑民
 * @dateTime: 2022/10/18 16:38
 */
@Data
@ApiModel("企业/机构注册DTO")
public class EnterpriseRegisterDTO {

    @ApiModelProperty("上级企业/机构id")
    private Long parentId;

    @ApiModelProperty("是否为机构，必填项")
    @NotNull(message = "是否为机构，isOrg不能为null")
    private Boolean isOrg;

    @ApiModelProperty("企业/机构简称")
    private String shortName;

    @ApiModelProperty("企业/机构全称")
    @NotBlank(message = "企业/机构全称，fullName不能为null")
    private String fullName;

    @ApiModelProperty("社会统一信用代码/组织机构代码")
    @NotBlank(message = "社会统一信用代码/组织机构代码，uniqueCode不能为null")
    private String uniqueCode;

    @ApiModelProperty("所在地址")
    @NotBlank(message = "所在地址，address不能为null")
    private String address;

    @ApiModelProperty("企业/机构邮箱")
    private String email;

    @ApiModelProperty("联系方式")
    private String mobile;

    @ApiModelProperty("传真")
    private String fax;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("法定代表人")
    private String legalPerson;

    @ApiModelProperty("法人联系方式")
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

package com.pandama.top.user.pojo.dto;

import com.pandama.top.core.pojo.dto.PageDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 搜索企业/机构信息传参
 *
 * @author 王强
 * @date 2023-07-08 15:49:49
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("企业/机构搜索DTO")
public class EnterpriseSearchDTO extends PageDTO {

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("社会统一信用代码")
    private String uniqueCode;

    @ApiModelProperty("所在地址")
    private String address;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("联系方式")
    private String mobile;

}

package com.pandama.top.user.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 图形验证码返回值
 * @author: 白剑民
 * @dateTime: 2023/4/21 14:37
 */
@Data
@ApiModel("图形验证码信息")
public class AuthCodeResultVO {

    @ApiModelProperty("验证码缓存key")
    private String uuid;

    @ApiModelProperty("验证码图片base64字符串")
    private String img;

    @ApiModelProperty("启用状态")
    private Boolean enabled;
}

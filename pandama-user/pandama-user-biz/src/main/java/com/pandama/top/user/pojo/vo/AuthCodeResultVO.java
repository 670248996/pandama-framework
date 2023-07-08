package com.pandama.top.user.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 图形验证码返回值
 *
 * @author 王强
 * @date 2023-07-08 15:52:11
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

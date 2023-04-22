package com.pandama.top.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description: 访问令牌信息
 * @author: 王强
 * @dateTime: 2022-11-02 13:48:11
 */
@Data
@ApiModel("token实体类")
public class AccessTokenInfo {

    @ApiModelProperty("前端访问后台时带上,在请求头设置Authorization=Bearer accessToken;注:Bearer与accessToken之间有一个空格")
    private String accessToken;

    @ApiModelProperty("刷新accessToken时用到")
    private String refreshToken;

    @ApiModelProperty("token类型")
    private String tokenType;

    @ApiModelProperty("token失效时间,单位:秒")
    private Integer expiresIn;

}

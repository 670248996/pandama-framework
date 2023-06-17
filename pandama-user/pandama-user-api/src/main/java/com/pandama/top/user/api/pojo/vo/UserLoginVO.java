package com.pandama.top.user.api.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @description: 用户登录信息
 * @author: 王强
 * @dateTime: 2022-08-16 09:54:28
 */
@Data
@ApiModel("用户登录信息")
public class UserLoginVO {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("账号")
    private String realName;

    @ApiModelProperty("账号是否启用")
    private Boolean status;

    @ApiModelProperty("重试次数")
    private Integer retryNum;

    @ApiModelProperty("冻结时长(单位: 小时, 默认5分钟)")
    private Integer freezeTime;

    @ApiModelProperty("角色代码列表")
    private List<String> roleCodeList;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;
}

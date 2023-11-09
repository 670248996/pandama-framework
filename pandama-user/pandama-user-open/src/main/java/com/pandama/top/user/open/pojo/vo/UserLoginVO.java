package com.pandama.top.user.open.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户登录出参
 *
 * @author 王强
 * @date 2023-07-08 15:39:59
 */
@Data
@ApiModel("用户登录信息")
public class UserLoginVO implements Serializable {

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("账号是否启用")
    private Boolean status;

    @ApiModelProperty("角色代码列表")
    private List<String> roleCodeList;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;
}

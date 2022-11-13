package com.pandama.top.utils.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用户登录信息
 * @author: 王强
 * @dateTime: 2022-08-16 09:54:28
 */
@Data
@ApiModel("用户登录信息")
public class UserLoginVO implements Serializable {

    private static final long serialVersionUID = 4586107564176031016L;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("编号")
    private String code;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;

    @ApiModelProperty("角色编号列表")
    private List<Integer> roleCodeList;

    @ApiModelProperty("权限编号列表")
    private List<Integer> permCodeList;

}

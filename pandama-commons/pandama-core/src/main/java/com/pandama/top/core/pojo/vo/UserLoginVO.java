package com.pandama.top.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
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
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("性别")
    private Integer gender;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;

    @ApiModelProperty("短信验证码")
    private String smsCode;

    @ApiModelProperty("密码到期时间")
    private LocalDateTime passwordExpireTime;

    @ApiModelProperty("角色编号列表")
    private List<String> roleCodeList;

    @ApiModelProperty("权限编号列表")
    private List<String> permCodeList;

    @ApiModelProperty("ip地址")
    private String ipAddress;

}

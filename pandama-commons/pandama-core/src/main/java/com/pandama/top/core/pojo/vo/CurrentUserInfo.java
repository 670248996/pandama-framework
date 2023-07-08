package com.pandama.top.core.pojo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 当前页用户信息
 *
 * @author 王强
 * @date 2023-07-08 15:13:15
 */
@Data
@ApiModel("当前用户信息")
public class CurrentUserInfo implements Serializable {

    private static final long serialVersionUID = 4586107564176031016L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("姓名")
    private String realName;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("是否是管理员")
    private Boolean isAdmin;

    @ApiModelProperty("角色编号列表")
    private List<String> roleCodeList;

    @ApiModelProperty("权限编号列表")
    private List<String> permCodeList;

    @ApiModelProperty("ip地址")
    private String ipAddress;


}

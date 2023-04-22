package com.pandama.top.gateway.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pandama.top.pojo.vo.UserLoginVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @description: 用户信息
 * @author: 王强
 * @dateTime: 2022-10-17 16:02:32
 */
@Data
public class User implements Serializable, UserDetails {
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

    private List<String> uriCodeList;

    @JsonIgnore
    private List<UserGrantedAuthority> uris;

    @Override
    public List<UserGrantedAuthority> getAuthorities() {
        return this.uris;
    }

    /**
     * @description: 账号是否未过期
     * @author: 王强
     * @date: 2022-10-18 12:54:11
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * @description: 账号是否未被锁定
     * @author: 王强
     * @date: 2022-10-18 12:54:13
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * @description: 用户账号凭证(密码)是否未过期
     * @author: 王强
     * @date: 2022-10-18 12:54:15
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * @description: 账户是否被启用
     * @author: 王强
     * @date: 2022-10-18 12:54:17
     * @return: boolean
     * @version: 1.0
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

    public List<UserGrantedAuthority> getUris() {
        return uris;
    }

}

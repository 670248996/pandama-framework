package com.pandama.top.gateway.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 用户信息
 * @author: 王强
 * @dateTime: 2022-10-17 16:02:32
 */
@Data
public class UserInfo implements Serializable, UserDetails {
    private static final long serialVersionUID = 4586107564176031016L;

    @ApiModelProperty("用户id")
    private Long id;

    @ApiModelProperty("账号")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("ip地址")
    private String ipAddress;

    @JsonIgnore
    @ApiModelProperty("授权信息列表")
    private List<UserGrantedAuthority> authorities;

    @Override
    public List<UserGrantedAuthority> getAuthorities() {
        return this.authorities;
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

}

package com.pandama.top.gateway.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 用户信息
 * @author: 王强
 * @dateTime: 2022-10-17 16:02:32
 */
@Data
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    @JsonIgnore
    private String password;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 是否管理
     */
    private Boolean isAdmin;

    /**
     * 角色代码列表
     */
    private List<String> roleCodeList;

    /**
     * 权限代码列表
     */
    @NotEmpty
    private List<String> permCodeList;

    /**
     * 资源代码列表
     */
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

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public List<UserGrantedAuthority> getUris() {
        return uris;
    }

    public void setUris(List<UserGrantedAuthority> uris) {
        this.uris = uris;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}

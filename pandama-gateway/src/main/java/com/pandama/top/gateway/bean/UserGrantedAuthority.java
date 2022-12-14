package com.pandama.top.gateway.bean;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

/**
 * @description: 用户授权信息，用于存放用户登陆后拥有的权限
 * @author: 王强
 * @dateTime: 2022-10-17 16:03:05
 */
public class UserGrantedAuthority implements GrantedAuthority {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private String uri;

    public UserGrantedAuthority() {
    }

    public UserGrantedAuthority(String uri) {
        this.uri = uri;
    }

    @Override
    public String getAuthority() {
        return uri;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UserGrantedAuthority) {
            return uri.equals(((UserGrantedAuthority) obj).uri);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.uri.hashCode();
    }

    @Override
    public String toString() {
        return this.uri;
    }

    public void setAuthority(String role) {
        this.uri = role;
    }
}

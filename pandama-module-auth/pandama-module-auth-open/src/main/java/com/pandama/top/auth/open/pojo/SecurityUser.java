package com.pandama.top.auth.open.pojo;

import com.pandama.top.auth.open.constant.MessageConstant;
import com.pandama.top.user.open.pojo.vo.UserLoginVO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
@Data
public class SecurityUser implements UserDetails {
    /**
     * ID
     */
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private Boolean enabled;
    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(UserLoginVO vo) {
        if (vo == null) {
            throw new UsernameNotFoundException(MessageConstant.USERNAME_PASSWORD_ERROR);
        }
        this.setId(vo.getId());
        this.setUsername(vo.getUsername());
        this.setPassword(vo.getPassword());
        this.setEnabled(vo.getStatus());
        if (vo.getRoleCodeList() != null) {
            this.setAuthorities(vo.getRoleCodeList().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}

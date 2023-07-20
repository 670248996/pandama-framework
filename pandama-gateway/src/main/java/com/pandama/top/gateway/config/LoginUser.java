package com.pandama.top.gateway.config;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author 王强
 * @date 2023-07-20 11:19:13
 */
@Data
public class LoginUser {

    private Long id;

    private String username;

    private String clientId;

    private List<String> scope;

    private List<String> authorities;

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        return this.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

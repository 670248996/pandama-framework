package com.pandama.top.auth.api.pojo;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Token信息
 * @author: 王强
 * @dateTime: 2023-06-21 22:14:20
 */
@Data
public class TokenInfo {

    private Long id;

    private String username;

    private String clientId;

    private List<String> scope;

    private List<String> authorities;

    public List<SimpleGrantedAuthority> getGrantedAuthorities() {
        return this.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }
}

package com.pandama.top.auth.api.pojo;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Token信息
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

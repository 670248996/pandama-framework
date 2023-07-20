package com.pandama.top.auth.api.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * Token返回信息封装
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class AccessToken {
    /**
     * 访问令牌
     */
    private String accessToken;
    /**
     * 刷新令牌
     */
    private String refreshToken;
    /**
     * 令牌类型
     */
    private String tokenType;
    /**
     * 有效时间（秒）
     */
    private Integer expiresIn;
    /**
     * 范围
     */
    private Set<String> scope;
}

package com.pandama.top.auth.biz.component;

import com.pandama.top.auth.api.pojo.SecurityUser;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT内容增强器
 *
 * @author 王强
 * @date 2023-07-08 11:53:23
 */
@Component
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (authentication.getPrincipal() instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            Map<String, Object> info = new HashMap<>(16);
            // 把用户ID设置到JWT中
            info.put("id", securityUser.getId());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
        }
        return accessToken;
    }
}
